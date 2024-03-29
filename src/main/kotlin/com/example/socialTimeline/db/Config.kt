package com.example.socialTimeline.db

import org.neo4j.cypherdsl.core.renderer.Dialect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.neo4j.config.EnableNeo4jAuditing
import org.neo4j.cypherdsl.core.renderer.Configuration as Neo4JConfiguration

@Configuration
@EnableNeo4jAuditing
class Configuration {

    @Bean
    fun cypherDslConfiguration(): Neo4JConfiguration {
        return Neo4JConfiguration.newConfig()
            .withDialect(Dialect.NEO4J_5).build();
    }
}

