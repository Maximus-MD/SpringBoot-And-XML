package com.cedacri.car_app;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

public class CarAppLauncherXml {
    public static void main(String[] args) throws LifecycleException {
        int port = 8081;
        String contextPath = "";
        String baseDir = new File(".").getAbsolutePath();

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.setBaseDir(baseDir);
        tomcat.getHost().setAppBase(baseDir);

        Connector connector = new Connector();
        connector.setPort(port);
        tomcat.getService().addConnector(connector);

        Context ctx = tomcat.addContext(contextPath, baseDir);
        ctx.addApplicationListener(ContextLoaderListener.class.getName());
        ctx.addParameter("contextConfigLocation", "src/main/webapp/WEB-INF/applicationContext.xml");

        XmlWebApplicationContext dispatcherContext = new XmlWebApplicationContext();
        dispatcherContext.setConfigLocation("src/main/webapp/WEB-INF/dispatcher-servlet.xml");

        DispatcherServlet dispatcherServlet = new DispatcherServlet(dispatcherContext);
        Tomcat.addServlet(ctx, "dispatcher", dispatcherServlet);
        ctx.addServletMappingDecoded("/", "dispatcher");

        tomcat.start();
        System.out.println("Tomcat started on http://localhost:" + port);
        tomcat.getServer().await();
    }
}
