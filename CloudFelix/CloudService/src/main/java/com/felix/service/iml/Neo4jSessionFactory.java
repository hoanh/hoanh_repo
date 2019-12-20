package com.felix.service.iml;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.config.ConfigurationSource;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
@Singleton
public class Neo4jSessionFactory {
	private SessionFactory sessionFactory;
    @PostConstruct
    public void init() {
    	ConfigurationSource props = new ClasspathConfigurationSource("ogm.properties");
    	Configuration configuration = new Configuration.Builder(props).build();
        sessionFactory = new SessionFactory(configuration,"com.felix.domain");
    }

    public Session getNeo4jSession() {
        return sessionFactory.openSession();
    }
}
