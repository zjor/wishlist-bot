package com.github.zjor.ext.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Arrays;
import java.util.stream.StreamSupport;

@Slf4j
public class ContextRefreshedEventHandler {

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        logEndpoints(event);
        logEnvironment(event);
    }

    private void logEndpoints(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
                .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        var map = requestMappingHandlerMapping
                .getHandlerMethods();
        log.info("=== [ List of all endpoints ] ===");
        map.forEach((key, value) -> {
            var keyStr = new StringBuilder();
            if (key.getMethodsCondition().isEmpty()) {
                keyStr.append('*');
            } else {
                keyStr.append(key.getMethodsCondition().getMethods());
            }
            keyStr.append(' ').append(key.getActivePatternsCondition());
            log.info("{} => {}", keyStr, value);
        });
    }

    private void logEnvironment(ContextRefreshedEvent event) {
        final Environment env = event.getApplicationContext().getEnvironment();
        log.info("====== Environment and configuration ======");
        log.info("Active profiles: {}", Arrays.toString(env.getActiveProfiles()));
        final MutablePropertySources sources = ((AbstractEnvironment) env).getPropertySources();
        StreamSupport.stream(sources.spliterator(), false)
                .filter(ps -> ps instanceof EnumerablePropertySource)
                .map(ps -> ((EnumerablePropertySource<?>) ps).getPropertyNames())
                .flatMap(Arrays::stream)
                .distinct()
                .forEach(prop -> {
                    boolean masked = Arrays.stream(prop.toLowerCase().split("\\."))
                            .anyMatch(s -> s.contains("secret") || s.contains("token") || s.contains("key") ||
                                    s.contains("password"));

                    log.info("{}: {}", prop, masked ? "**** masked ****" : env.getProperty(prop));
                });
        log.info("===========================================");
    }

}
