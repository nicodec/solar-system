package meli.nicolas.deciancio.solar.system.config;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.google.common.base.Preconditions;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ImportResource(value = "classpath*:application-context.xml")
public class PersistenceConfig implements DisposableBean {

    @Value("${solar.system.sql.driver_class}")
    private String driverClass;

    @Value("${solar.system.sql.url}")
    private String jdbcUrl;

    @Value("${solar.system.sql.username}")
    private String username;

    @Value("${solar.system.sql.password}")
    private String password;

    @Value("${solar.system.sql.c3p0.min_size}")
    private int c3p0MinSize;

    @Value("${solar.system.sql.c3p0.max_size}")
    private int c3p0MaxSize;

    @Value("${solar.system.hibernate.checkout.timeout}")
    private int checkoutTimeout;

    @Value("${solar.system.hibernate.dialect}")
    private String dialect;

    @Value("${solar.system.hibernate.bytecode.use_reflection_optimizer}")
    private String bytecodeUseReflectionOptimizer;

    @Value("${solar.system.hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Value("${solar.system.hibernate.show_sql}")
    private String showSql;

    @Value("${solar.system.hibernate.format_sql}")
    private String formatSql;

    @Value("${solar.system.hibernate.generate_statistics}")
    private String generateStatistics;

    @Value("${solar.system.hibernate.autocommit}")
    private String hibernateConnectionAutocommit;

    // defined on construct
    private HikariDataSource dataSource;

    @Bean(name = "dataSource")
    public HikariDataSource dataSource() {
        final HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClass);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setMinimumIdle(c3p0MinSize);
        config.setMaximumPoolSize(c3p0MaxSize);
        config.setConnectionTimeout(checkoutTimeout);

        this.dataSource = new HikariDataSource(config);
        return this.dataSource;
    }

    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        final String[] packagesToScan = new String[]{"meli.nicolas.deciancio.solar.system.model.entity"};

        sessionFactory.setPackagesToScan(packagesToScan);
        sessionFactory.setDataSource(this.dataSource());

        final Properties hibernateProperties = this.hibernateProperties();

        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", dialect);
        hibernateProperties.setProperty("hibernate.bytecode.use_reflection_optimizer", bytecodeUseReflectionOptimizer);

        Preconditions.checkArgument(!"CREATE".equalsIgnoreCase(hbm2ddlAuto) || StringUtils.containsIgnoreCase(jdbcUrl, "LOCALHOST"), "hbm2ddlAuto=CREATE allowed only for localhost environment databases");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        hibernateProperties.setProperty("hibernate.show_sql", showSql);
        hibernateProperties.setProperty("hibernate.format_sql", formatSql);
        hibernateProperties.setProperty("hibernate.generate_statistics", generateStatistics);
        hibernateProperties.setProperty("hibernate.connection.autocommit", hibernateConnectionAutocommit);
        hibernateProperties.setProperty("hibernate.connection.zeroDateTimeBehavior", "convertToNull");
        hibernateProperties.setProperty("hibernate.connection.transformedBitIsBoolean", "true");
        hibernateProperties.setProperty("hibernate.flushMode", "ALWAYS");
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", "800");

        return hibernateProperties;
    }

    @Bean(name = "transactionManager")
    public HibernateTransactionManager transactionManager() {
        final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        final SessionFactory sessionFactory = this.sessionFactory().getObject();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    @Override
    public void destroy() {
        this.dataSource.close();
    }
}
