package com.example.cassandracrud.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.*;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(
        basePackages = "com.example.cassandracrud")
//This is not mandatory configuration to connect to Cassandra DB
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspaceName;

    @Value("${spring.data.cassandra.port}")
    private int port;

    @Value("${spring.data.cassandra.contact-points}")
    private String contactPoints;

    @Value("${spring.data.cassandra.cluster}")
    private String clusterName;

    //Not available in AbstractCassandraConfiguration
    @Value("${spring.data.cassandra.local-datacenter}")
    private String localDatacenter;


    @Override
    protected String getKeyspaceName() {
        return keyspaceName;
    }

    @Override
    public String getContactPoints() {
        return contactPoints;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getClusterName() {
        return clusterName;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

//    @Override
//    protected String getLocalDataCenter() {
//        return localDatacenter;
//    }

//    Available in 2.0.5.RELEASE
//    @Bean
//    public CassandraClusterFactoryBean cluster() {
//        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
//        //PlainTextAuthProvider sap = new PlainTextAuthProvider(username, passsword);
//        cluster.setContactPoints(contactPoints);
//        cluster.setPort(port);
//        cluster.setClusterName(clusterName);
//        //cluster.setAuthProvider(sap);
//        cluster.setJmxReportingEnabled(false);
//        return cluster;
//    }
}
