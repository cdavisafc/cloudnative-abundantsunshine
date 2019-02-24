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

import java.util.HashSet;
import java.util.Set;

public class Utils implements ApplicationContextAware, ApplicationListener<ApplicationEvent> {

    private ApplicationContext applicationContext;
    private int port;
    @Value("${ipaddress}")
    private String ip;
    @Value("${com.corneliadavis.cloudnative.connections.secrets}")
    private String configuredSecretsIn;
    private Set<String> configSecrets;

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
        } else if (applicationEvent instanceof ApplicationPreparedEvent) {
            configSecrets = new HashSet<>();
            String secrets[] = configuredSecretsIn.split(",");
            for (int i=0; i<secrets.length; i++)
                configSecrets.add(secrets[i].trim());
            logger.info(ipTag() + "Connections Service initialized with secret(s): " + configuredSecretsIn);
        }
    }

    public String ipTag() { return "[" + ip + ":" + port +"] "; }

    public boolean isValidSecret(String secret) { return configSecrets.contains(secret); }

    public String validSecrets() {
        String result = "";
        for (String s : configSecrets)
            result += s + ",";
        return result;
    }

}
