package com.content.management.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;


@Configuration
@EnableNeo4jRepositories(basePackages = "com.content.management.repository")
public class NeoConfig {
    /*public static final String URL = System.getenv("NEO4J_URL") != null ? System.getenv("NEO4J_URL") : "http://neo4j:content@localhost:7474";

    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
        return new Builder().uri(URL).build();
    }

    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(), "com.content.management.repository.entities");
    }*/
}
