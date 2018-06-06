package com.cache.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ParentApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("**************** I am an event in the parent context *********************************");
        Arrays.asList(contextRefreshedEvent.getApplicationContext().getBeanDefinitionNames())
                .forEach(s -> System.out.println("Bean Name = "+s));
        System.out.println(contextRefreshedEvent.getApplicationContext());
    }
}
