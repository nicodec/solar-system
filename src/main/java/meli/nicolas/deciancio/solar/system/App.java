package meli.nicolas.deciancio.solar.system;

import static org.slf4j.LoggerFactory.getLogger;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.slf4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.DispatcherServlet;

public class App {

    private static final Logger LOGGER = getLogger(App.class);

    private static final String APPLICATION_CONTEXT_PATH = "classpath:application-context.xml";

    private static final String APP_SERVLET_CONFIG_PATH = "classpath:mvc-servlet.xml";

    public static void main( String[] args ) throws Exception {
        int exitStatus = 0;
        Server server = null;

        try {
            // load webAppContext
            final ServletContextHandler servletContextHandler = getAppContext("/solar-system/*");

            server = new Server(9290);
            server.setHandler(servletContextHandler);

            final MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
            server.addEventListener(mbContainer);
            server.addBean(mbContainer);
            server.addBean(Log.getLog());
            final String hostName = InetAddress.getLocalHost().getHostName();

            server.start();
            LOGGER.info("server started at: {}:{}{}", hostName, 9290, "/solar-system/*");
            server.join();
        } catch (final InterruptedException e) {
            LOGGER.info("application server interrupted. Finish jvm with ok.");
        } catch (final Exception e) {
            LOGGER.error("application server raise exception. Finish jvm with error: ", e);
            exitStatus = 1;
        } finally {
            if (server != null) {
                server.stop();
                server.destroy();
            }
            LOGGER.info("application end");
        }
        // no-sonar
        System.exit(exitStatus);
    }

    private static ServletContextHandler getAppContext(String webAppPath) {
        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS | ServletContextHandler.NO_SECURITY);
        context.addEventListener(new ContextLoaderListener());
        context.setInitParameter("contextConfigLocation", APPLICATION_CONTEXT_PATH);

        // Adding Spring dispacherServlets

        final DispatcherServlet dispatcherLegacy = new DispatcherServlet();
        dispatcherLegacy.setContextConfigLocation(APP_SERVLET_CONFIG_PATH);
        final ServletHolder servletLegacy = new ServletHolder(dispatcherLegacy);
        servletLegacy.setInitOrder(1);
        servletLegacy.setName("solar-system");
        servletLegacy.setDisplayName("solar-system");
        context.addServlet(servletLegacy, webAppPath);

        context.setCompactPath(true);
        // Add alias check logic to context
        context.addAliasCheck(new ContextHandler.ApproveAliases());

        LOGGER.info("determined application's path: {}", webAppPath);
        return context;
    }
}
