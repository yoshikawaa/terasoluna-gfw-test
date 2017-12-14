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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class JdbcSelector<T> {

    private static final Logger logger = LoggerFactory.getLogger(JdbcSelector.class);

    private static final String SQL_TEMPLATE_SELECT = "SELECT * FROM";
    private static final String SQL_TEMPLATE_WHERE = "WHERE";
    private static final String SQL_TEMPLATE_WHERE_VALUE_SEPARATOR = "=?";
    private static final String SQL_TEMPLATE_WHERE_AND_SEPARATOR = " AND ";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<T> rowMapper;
    private final String sqlFindAll;
    private final String sqlFindOne;

    public JdbcSelector(JdbcTemplate jdbcTemplate, RowMapper<T> rowMapper, String tableName, String... primaryKeys) {

        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;

        this.sqlFindAll = String.join(" ", SQL_TEMPLATE_SELECT, tableName);
        this.sqlFindOne = (primaryKeys == null || primaryKeys.length == 0) ? null
                : String.join(" ", sqlFindAll, SQL_TEMPLATE_WHERE,
                        Arrays.stream(primaryKeys).map(k -> k + SQL_TEMPLATE_WHERE_VALUE_SEPARATOR).collect(
                                Collectors.joining(SQL_TEMPLATE_WHERE_AND_SEPARATOR)));
    }

    public List<T> findAll() {
        Assert.notNull(sqlFindAll, "find all statement is not available.");
        logger.debug("[JdbcSelector][Prepared SQL]", sqlFindAll);
        return jdbcTemplate.query(sqlFindAll, rowMapper);
    }

    public T findOne(Object... args) {
        Assert.notNull(sqlFindOne, "find one statement is not available.");
        logger.debug("[JdbcSelector][Prepared SQL]", sqlFindOne);
        logger.debug("[JdbcSelector][Parameters  ]{}", args);
        return jdbcTemplate.queryForObject(sqlFindOne, args, rowMapper);
    }

}
