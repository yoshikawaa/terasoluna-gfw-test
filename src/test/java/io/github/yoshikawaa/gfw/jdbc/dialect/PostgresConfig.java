package io.github.yoshikawaa.gfw.jdbc.dialect;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.postgresql.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostgresConfig {
    @Bean
    public DataSource dataSource() {
        return new BasicDataSource() {
            {
                setDriver(new Driver());
                setUrl("jdbc:postgresql://localhost:5432/travis_ci_test");
                setUsername("postgres");
                setPassword("");
            }
        };
    }
}
