package io.github.yoshikawaa.gfw.jdbc.dialect;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.h2.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2Config {
    @Bean
    public DataSource dataSource() {
        return new BasicDataSource() {
            {
                setDriver(new Driver());
                setUrl("jdbc:h2:mem:travis_ci_test;DB_CLOSE_DELAY=-1");
                setUsername("sa");
                setPassword("");
            }
        };
    }
}
