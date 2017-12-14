/**
 * Copyright (c) 2017 Atsushi Yoshikawa (https://yoshikawaa.github.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.yoshikawaa.gfw.test.jdbc;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class JdbcSelectorFactory {

    private final JdbcTemplate jdbcTemplate;
    private RowMapperCreationStrategy rowMapperCreationStrategy = new DefaultRowMapperCreationStrategy();
    private PrimaryKeysObtainStrategy primaryKeysObtainStrategy;

    public JdbcSelectorFactory(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public JdbcSelectorFactory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public <T> JdbcSelector<T> build(RowMapper<T> rowMapper, String tableName, String... primaryKeys) {
        Assert.notNull(jdbcTemplate, "JdbcTemplate must not null.");

        return new JdbcSelector<>(jdbcTemplate, rowMapper, tableName, primaryKeys);
    }

    public <T> JdbcSelector<T> create(Class<T> entityClass, String... primaryKeys) {
        Assert.notNull(jdbcTemplate, "JdbcTemplate must not null.");
        Assert.notNull(rowMapperCreationStrategy, "RowMapperStrategy must not null.");

        return new JdbcSelector<>(jdbcTemplate, rowMapperCreationStrategy.create(entityClass),
                entityClass.getSimpleName(), primaryKeys);
    }

    public <T> JdbcSelector<T> create(Class<T> entityClass) {
        Assert.notNull(jdbcTemplate, "JdbcTemplate must not null.");
        Assert.notNull(rowMapperCreationStrategy, "RowMapperStrategy must not null.");
        Assert.notNull(primaryKeysObtainStrategy, "PrimaryKeysStrategy must not null.");

        return new JdbcSelector<>(jdbcTemplate, rowMapperCreationStrategy.create(entityClass),
                entityClass.getSimpleName(),
                primaryKeysObtainStrategy.obtainPrimaryKeys(jdbcTemplate, entityClass.getSimpleName()));
    }

    public void setRowMapperCreationStrategy(RowMapperCreationStrategy rowMapperCreationStrategy) {
        this.rowMapperCreationStrategy = rowMapperCreationStrategy;
    }

    public void setPrimaryKeysObtainStrategy(PrimaryKeysObtainStrategy primaryKeysObtainStrategy) {
        this.primaryKeysObtainStrategy = primaryKeysObtainStrategy;
    }

}
