package com.corneliadavis.cloudnative.connections.projection;

import com.corneliadavis.cloudnative.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.List;

public class IdManager implements ApplicationContextAware, ApplicationListener<ApplicationEvent> {
    private Long maxUserId;
    private Long maxConnectionId;

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    private ApplicationContext applicationContext;

    @Autowired
    public IdManager() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        if (applicationEvent instanceof ServletWebServerInitializedEvent) {
            UserRepository userRepository = applicationContext.getBean(UserRepository.class);
            List<Long> max = userRepository.getMaxId();
            if (max.get(0) == null)
                this.maxUserId = 0L;
            else
                this.maxUserId = max.get(0);

            ConnectionRepository connectionRepository = applicationContext.getBean(ConnectionRepository.class);
            max = connectionRepository.getMaxId();
            if (max.get(0) == null)
                this.maxConnectionId = 0L;
            else
                this.maxConnectionId = max.get(0);

        }
    }

    public Long nextUserId() {
        this.maxUserId = this.maxUserId + 1;
        return this.maxUserId;
    }

    public Long nextConnectionId() {
        this.maxConnectionId = this.maxConnectionId + 1;
        return this.maxConnectionId;
    }
}
