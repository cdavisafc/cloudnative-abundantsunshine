package com.corneliadavis.cloudnative;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

public class Utils implements ApplicationContextAware, ApplicationListener<ServletWebServerInitializedEvent> {

    private ApplicationContext applicationContext;
    private int port;
    @Value("${ipaddress}")
    private String ip;

    public String ipTag() {

        return "[" + ip + ":" + port +"] ";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent embeddedServletContainerInitializedEvent) {
        this.port = embeddedServletContainerInitializedEvent.getApplicationContext().getWebServer().getPort();
    }
}