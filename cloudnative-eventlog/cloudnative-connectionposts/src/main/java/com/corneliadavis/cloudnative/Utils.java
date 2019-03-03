package com.corneliadavis.cloudnative;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
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
        if (applicationEvent instanceof ServletWebServerInitializedEvent) {
            ServletWebServerInitializedEvent servletWebServerInitializedEvent
                    = (ServletWebServerInitializedEvent) applicationEvent;
            this.port = servletWebServerInitializedEvent.getApplicationContext().getWebServer().getPort();
        }
    }

    public String ipTag() { return "[" + ip + ":" + port +"] "; }

}
