package com.corneliadavis.cloudnative;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

public class Utils implements ApplicationContextAware, ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    private ApplicationContext applicationContext;
    private int port;
    @Value("${INSTANCE_IP}")
    private String ip;

    public String ipTag() {

        return "[" + ip + ":" + port +"] ";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent embeddedServletContainerInitializedEvent) {
        EmbeddedWebApplicationContext webAppContext = (EmbeddedWebApplicationContext) applicationContext;
        EmbeddedServletContainer cont = webAppContext.getEmbeddedServletContainer();
        this.port = cont.getPort();
    }
}
