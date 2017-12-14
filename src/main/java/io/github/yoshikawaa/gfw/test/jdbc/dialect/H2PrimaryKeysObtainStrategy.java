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
package io.github.yoshikawaa.gfw.test.jdbc.dialect;

import org.springframework.jdbc.core.JdbcTemplate;

import io.github.yoshikawaa.gfw.test.jdbc.PrimaryKeysObtainStrategy;

public class H2PrimaryKeysObtainStrategy implements PrimaryKeysObtainStrategy {

    private static final String SQL = "SELECT COLUMN_LIST FROM INFORMATION_SCHEMA.CONSTRAINTS WHERE CONSTRAINT_SCHEMA = SCHEMA() AND TABLE_NAME=?";
    private static final String PRIMARY_KEYS_SEPARATOR = ",";

    @Override
    public String[] obtainPrimaryKeys(JdbcTemplate jdbcTemplate, String tableName) {
        return jdbcTemplate.queryForObject(SQL, String.class, tableName.toUpperCase())
                .split(PRIMARY_KEYS_SEPARATOR);
    }

}
