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
package io.github.yoshikawaa.gfw.test.context.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

/**
 * {@link Sql} configured with phase {@link ExecutionPhase#BEFORE_TEST_METHOD}
 * 
 * @author Atsushi Yoshikawa
 * @see Sql
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public @interface SqlBefore {

    @AliasFor(annotation = Sql.class)
    String[] value() default {};

    @AliasFor(annotation = Sql.class)
    String[] scripts() default {};

    @AliasFor(annotation = Sql.class)
    String[] statements() default {};

}