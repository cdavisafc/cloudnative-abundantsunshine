package com.corneliadavis.cloudnative.posts.projection;

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
    private Long maxId;

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    private ApplicationContext applicationContext;
    //private PostRepository postRepository;

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
            PostRepository postRepository = applicationContext.getBean(PostRepository.class);
            //this.maxId = postRepository.getMaxId();
            List<Long> foo = postRepository.getMaxId();
            if (foo.get(0) == null)
                this.maxId = 0L;
            else
                this.maxId = foo.get(0);
            logger.info("maxId initialized to " + this.maxId);
        }
    }

    public Long nextId() {
        this.maxId = this.maxId + 1;
        return this.maxId;
    }
}
