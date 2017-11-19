package jp.yoshikawaa.gfw.test.web.servlet.result;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Constructor;

import org.junit.Test;

public class TerasolunaGfwMockMvcResultMatchersTest {

    @Test
    public void testConstructor() throws Exception {
        Constructor<?> constructor = TerasolunaGfwMockMvcResultMatchers.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThat(constructor.newInstance(), notNullValue());
    }

}
