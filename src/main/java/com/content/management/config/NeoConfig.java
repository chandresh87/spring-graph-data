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
     @Bean
    public org.neo4j.ogm.config.Configuration configuration() {

        return new org.neo4j.ogm.config.Configuration.Builder()
                .autoIndex("assert")
                .withCustomProperty("dbms.index_sampling.background_enabled", true)
                .withCustomProperty("dbms.index_sampling.update_percentage", 5)
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "bolt.enabled", havingValue = "true", matchIfMissing = false)
    public GraphDatabaseService graphDatabaseService(
            @Value("${neo4j.file}") String neo4JFile,
            @Value("${bolt.type}") String boltType,
            @Value("${bolt.enabled}") String boltEnabled,
            @Value("${bolt.address}") String boltAddress) {

        BoltConnector bolt = new BoltConnector("0");

        return new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder(new File(neo4JFile))
                .setConfig(bolt.type, boltType)
                .setConfig(bolt.enabled, boltEnabled)
                .setConfig(bolt.listen_address, boltAddress)
                .setConfig(GraphDatabaseSettings.index_background_sampling_enabled, "true")
                .setConfig(GraphDatabaseSettings.index_sampling_update_percentage, "5")
                .newGraphDatabase();
    }

    @Bean
    @ConditionalOnProperty(name = "bolt.enabled", havingValue = "true", matchIfMissing = false)
    public EmbeddedDriver embeddedDriver(GraphDatabaseService graphDatabaseService, org.neo4j.ogm.config.Configuration configuration) {

        return new EmbeddedDriver(graphDatabaseService, configuration);
    }


    @Bean(name="sessionFactory")
    @ConditionalOnProperty(prefix ="bolt", name = "enabled", havingValue = "true")
    public SessionFactory sessionFactoryBoltEnabled(EmbeddedDriver embeddedDriver) {

        return new SessionFactory(embeddedDriver, AttributeDefinition.class.getPackage().getName(),
                ModelVariant.class.getPackage()
                        .getName(), AttributeDefinitionWithRelevance.class.getPackage().getName());
    }

    @Bean(name="sessionFactory")
    @ConditionalOnProperty(prefix ="bolt", name = "enabled", havingValue = "false")
    public SessionFactory sessionFactoryBoltDisabled(org.neo4j.ogm.config.Configuration configuration) {

        return new SessionFactory(configuration, AttributeDefinition.class.getPackage().getName(),
                ModelVariant.class.getPackage()
                        .getName(), AttributeDefinitionWithRelevance.class.getPackage().getName());
    }

    @Bean(name = "neo4jTransactionManager")
    public Neo4jTransactionManager transactionManager(SessionFactory sessionFactory) {

        return new Neo4jTransactionManager(sessionFactory);
    }

    @Bean(name = "graphTransactionManager")
    @Primary
    public PlatformTransactionManager graphTransactionManager(
            @Qualifier("neo4jTransactionManager") Neo4jTransactionManager transactionManager) {

        return transactionManager;

    }
}
