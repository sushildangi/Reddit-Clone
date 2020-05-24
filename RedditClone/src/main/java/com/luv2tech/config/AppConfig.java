package com.luv2tech.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.persistence.EntityListeners;


@Configuration
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
@EnableAsync
public class AppConfig {
}
