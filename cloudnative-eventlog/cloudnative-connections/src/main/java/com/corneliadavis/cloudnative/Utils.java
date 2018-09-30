package com.corneliadavis.cloudnative;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class Utils implements ApplicationContextAware, ApplicationListener<ApplicationEvent> {

    private ApplicationContext applicationContext;
    private int port;
    @Value("${ipaddress}")
    private String ip;

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof EmbeddedServletContainerInitializedEvent) {
            EmbeddedWebApplicationContext webAppContext = (EmbeddedWebApplicationContext) applicationContext;
            EmbeddedServletContainer cont = webAppContext.getEmbeddedServletContainer();
            this.port = cont.getPort();
        }
    }

    public String ipTag() { return "[" + ip + ":" + port +"] "; }

}
