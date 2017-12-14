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

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.common.base.CaseFormat;

import io.github.yoshikawaa.gfw.test.util.ReflectionUtils;

public class DefaultRowMapperCreationStrategy implements RowMapperCreationStrategy {

    @Override
    public <T> RowMapper<T> create(Class<T> entityClass) {
        return new RowMapper<T>() {
            @Override
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                Set<String> columns = getColumnNames(rs.getMetaData());
                T entity = ReflectionUtils.newInstance(entityClass);
                for (Field field : entityClass.getDeclaredFields()) {
                    String columnName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
                    if (columns.contains(columnName)) {
                        ReflectionTestUtils.setField(entity, field.getName(), getColumn(rs, columnName, field.getType()));
                    }
                }
                return entity;
            }
        };
    }

    protected Set<String> getColumnNames(ResultSetMetaData metaData) throws SQLException {
        Set<String> columns = new HashSet<>();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            columns.add(metaData.getColumnName(i).toLowerCase());
        }
        return columns;
    }

    protected Object getColumn(ResultSet rs, String columnName, Class<?> type) throws SQLException {
        if (Date.class.isAssignableFrom(type))
            return rs.getDate(columnName);
        else if (Integer.class.isAssignableFrom(type))
            return rs.getInt(columnName);
        else if (Long.class.isAssignableFrom(type))
            return rs.getLong(columnName);
        else if (BigDecimal.class.isAssignableFrom(type))
            return rs.getBigDecimal(columnName);
        else if (Boolean.class.isAssignableFrom(type))
            return rs.getBoolean(columnName);
        else if (String.class.isAssignableFrom(type))
            return rs.getString(columnName);
        else if (Blob.class.isAssignableFrom(type))
            return rs.getBlob(columnName);
        else
            return rs.getObject(columnName);
    }
}
