package com.github.zjor.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.jooq.ConnectionProvider;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@Slf4j
//@Configuration
public class JOOQConfiguration {

    @Bean
    public org.jooq.Configuration jooqConfiguration(DataSource dataSource) {
        log.info("DataSource className: {}", dataSource.getClass().getName());
        if (dataSource instanceof HikariDataSource) {
            log.info("DataSource pool size: {}", ((HikariDataSource) dataSource).getMaximumPoolSize());
        }
        // To support native jOOQ transactions instead of spring annotations
        ConnectionProvider connectionProvider = new DataSourceConnectionProvider(dataSource);
        return new DefaultConfiguration()
                .set(connectionProvider)
                .set(new ThreadLocalTransactionProvider(connectionProvider))
                .set(SQLDialect.POSTGRES);
    }

}
