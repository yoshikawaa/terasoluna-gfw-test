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

import java.util.StringJoiner;

import org.springframework.jdbc.core.JdbcTemplate;

import io.github.yoshikawaa.gfw.test.jdbc.PrimaryKeysObtainStrategy;

public class PostgresPrimaryKeysObtainStrategy implements PrimaryKeysObtainStrategy {

    private static final String SQL;
    private static final String PRIMARY_KEY_COLUMN_NAME = "COLUMN_NAME";

    static {
        SQL = new StringJoiner(" ")
                .add("SELECT DISTINCT")
                .add("    COL.COLUMN_NAME      AS COLUMN_NAME")
                .add("   ,COL.ORDINAL_POSITION AS POSITION")
                .add("FROM")
                .add(" INFORMATION_SCHEMA.TABLE_CONSTRAINTS TC")
                .add(" INNER JOIN INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE CCU ON")
                .add("    TC.TABLE_NAME      = CCU.TABLE_NAME")
                .add("AND TC.CONSTRAINT_NAME = CCU.CONSTRAINT_NAME")
                .add("INNER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE COL ON")
                .add("    TC.TABLE_CATALOG   = COL.TABLE_CATALOG")
                .add("AND TC.TABLE_SCHEMA    = COL.TABLE_SCHEMA")
                .add("AND TC.TABLE_NAME      = COL.TABLE_NAME")
                .add("AND CCU.COLUMN_NAME    = COL.COLUMN_NAME")
                .add("WHERE")
                .add("    TC.CONSTRAINT_TYPE = 'PRIMARY KEY'")
                .add("AND TC.TABLE_NAME = ?")
                .add("ORDER BY")
                .add("    COL.ORDINAL_POSITION")
                .toString();
    }

    @Override
    public String[] obtainPrimaryKeys(JdbcTemplate jdbcTemplate, String tableName) {
        return jdbcTemplate.queryForList(SQL, tableName)
                .stream()
                .map(row -> row.get(PRIMARY_KEY_COLUMN_NAME).toString())
                .toArray(size -> new String[size]);
    }

}
