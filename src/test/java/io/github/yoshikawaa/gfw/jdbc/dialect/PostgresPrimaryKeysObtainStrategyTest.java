package io.github.yoshikawaa.gfw.jdbc.dialect;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.yoshikawaa.gfw.test.jdbc.PrimaryKeysObtainStrategy;
import io.github.yoshikawaa.gfw.test.jdbc.dialect.PostgresPrimaryKeysObtainStrategy;

@RunWith(SpringRunner.class)
@IfProfileValue(name = "database.postgresql", value = "true")
@ContextConfiguration(classes = PostgresConfig.class)
@Sql(statements = { "drop table if exists todo", "drop table if exists todo_complex",
        "create table todo(todo_id varchar(36) not null, todo_title varchar(30), finished boolean, created_at timestamp, constraint pk_todo primary key (todo_id))",
        "create table todo_complex(todo_id varchar(36) not null, todo_title varchar(30), finished boolean, created_at timestamp, constraint pk_todo_complex primary key (todo_id, todo_title))", })
public class PostgresPrimaryKeysObtainStrategyTest {

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void testSimplePrimaryKey() {
        PrimaryKeysObtainStrategy strategy = new PostgresPrimaryKeysObtainStrategy();
        String[] primaryKeys = strategy.obtainPrimaryKeys(jdbcTemplate, "todo");

        assertThat(primaryKeys.length, is(1));
        assertThat(primaryKeys[0], is("todo_id"));
    }

    @Test
    public void testComplexPrimaryKey() {
        PrimaryKeysObtainStrategy strategy = new PostgresPrimaryKeysObtainStrategy();
        String[] primaryKeys = strategy.obtainPrimaryKeys(jdbcTemplate, "todo_complex");

        assertThat(primaryKeys.length, is(2));
        assertThat(primaryKeys[0], is("todo_id"));
        assertThat(primaryKeys[1], is("todo_title"));
    }

}
