package com.content.management.config;


import org.neo4j.configuration.connectors.BoltConnector;
import org.neo4j.configuration.helpers.SocketAddress;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.io.File;

import static org.neo4j.configuration.GraphDatabaseSettings.DEFAULT_DATABASE_NAME;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.content.management.repository")
public class NeoConfig {

    private static void registerShutdownHook(final DatabaseManagementService managementService) {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                managementService.shutdown();
            }
        });
    }

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
    public GraphDatabaseService startServer() {
        File databaseDirectory = new File("/");

        DatabaseManagementService managementService = new DatabaseManagementServiceBuilder(databaseDirectory.toPath()).setConfig(BoltConnector.enabled, true)
                .setConfig(BoltConnector.listen_address, new SocketAddress("localhost", 7687)).build();
        registerShutdownHook(managementService);
        GraphDatabaseService graphDb = managementService.database(DEFAULT_DATABASE_NAME);
        return graphDb;
    }

   /* @Bean
    @ConditionalOnProperty(name = "bolt.enabled", havingValue = "true", matchIfMissing = false)
    public GraphDatabaseService graphDatabaseService(
            @Value("${neo4j.file}") String neo4JFile,
            @Value("${bolt.type}") String boltType,
            @Value("${bolt.enabled}") String boltEnabled,
            @Value("${bolt.address}") String boltAddress) {

        BoltConnector bolt = new BoltConnector();

        return new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder(new File(neo4JFile))
                .setConfig(bolt.type, boltType)
                .setConfig(bolt.enabled, boltEnabled)
                .setConfig(bolt.listen_address, boltAddress)
                .setConfig(GraphDatabaseSettings.index_background_sampling_enabled, "true")
                .setConfig(GraphDatabaseSettings.index_sampling_update_percentage, "5")
                .newGraphDatabase();
    }*/

    /*@Bean
    @ConditionalOnProperty(name = "bolt.enabled", havingValue = "true", matchIfMissing = false)
    public EmbeddedDriver embeddedDriver(GraphDatabaseService graphDatabaseService, org.neo4j.ogm.config.Configuration configuration) {

        return new EmbeddedDriver(graphDatabaseService, configuration);
    }


    @Bean(name = "sessionFactory")
    @ConditionalOnProperty(prefix = "bolt", name = "enabled", havingValue = "true")
    public SessionFactory sessionFactoryBoltEnabled(EmbeddedDriver embeddedDriver) {

        return new SessionFactory(embeddedDriver);
    }

    @Bean(name = "sessionFactory")
    @ConditionalOnProperty(prefix = "bolt", name = "enabled", havingValue = "false")
    public SessionFactory sessionFactoryBoltDisabled(org.neo4j.ogm.config.Configuration configuration) {

        return new SessionFactory(configuration);
    }*/

   /* @Bean(name = "neo4jTransactionManager")
    public Neo4jTransactionManager transactionManager(SessionFactory sessionFactory) {

        return new Neo4jTransactionManager(sessionFactory);
    }

    @Bean(name = "graphTransactionManager")
    @Primary
    public PlatformTransactionManager graphTransactionManager(
            @Qualifier("neo4jTransactionManager") Neo4jTransactionManager transactionManager) {

        return transactionManager;

    }*/
}
