package meli.nicolas.deciancio.solar.system.dao;

import static meli.nicolas.deciancio.solar.system.utils.CollectionUtils.safeStream;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import meli.nicolas.deciancio.solar.system.model.entity.Forecast;

@Repository
@Transactional
public class ForecastDao {

    //autowired by setter
    private SessionFactory sessionFactory;

    @Autowired
    public void init(LocalSessionFactoryBean factory) {
        this.sessionFactory = factory.getObject();
    }

    @SuppressWarnings("unchecked")
    public Optional<Forecast> findByDay(Long day){
        final Session session = this.sessionFactory.getCurrentSession();
        final Criteria criteria = session.createCriteria(Forecast.class);
        criteria.add(eq("day", day));
        return safeStream(criteria.list()).findFirst();
    }

    public Optional<Forecast> save(Forecast entity) {
        final Session session = this.sessionFactory.getCurrentSession();
        session.save(entity);
        return Optional.ofNullable(entity);
    }

    public void saveAll(Iterable<Forecast> entities) {
        final Session session = this.sessionFactory.getCurrentSession();
        for (final Forecast entity : entities) {
            session.save(entity);
        }
    }
}

