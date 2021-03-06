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
package io.github.yoshikawaa.gfw.test.context.web;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * {@link ContextConfiguration} for testing web application layer.
 * <p>
 * Include the following context files.
 * </p>
 * <ul>
 * <li>{@code applicationContext.xml}</li>
 * <li>{@code spring-security.xml}</li>
 * <li>{@code spring-mvc.xml}</li>
 * </ul>
 *
 * @author Atsushi Yoshikawa
 * @see WebAppConfiguration
 * @see ContextConfiguration
 * @see ContextHierarchy
 */
@Documented
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration({ "classpath*:META-INF/spring/applicationContext.xml",
                "classpath*:META-INF/spring/spring-security.xml" }),
        @ContextConfiguration("classpath*:META-INF/spring/spring-mvc.xml") })
public @interface TerasolunaGfwWebAppTest {

}
