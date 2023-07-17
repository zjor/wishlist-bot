package com.github.zjor.config;

import org.jooq.ConnectionProvider;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JOOQConfiguration {

    @Bean
    public org.jooq.Configuration jooqConfiguration(DataSource dataSource) {
        // To support native jOOQ transactions instead of spring annotations
        ConnectionProvider connectionProvider = new DataSourceConnectionProvider(dataSource);
        return new DefaultConfiguration()
                .set(connectionProvider)
                .set(new ThreadLocalTransactionProvider(connectionProvider))
                .set(SQLDialect.POSTGRES);
    }

}
