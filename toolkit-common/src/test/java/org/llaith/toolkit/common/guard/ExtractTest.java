package org.llaith.toolkit.common.guard;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;

/**
 *
 */
public class ExtractTest {

    @Test
    public void testFirstResultMatch() throws Exception {

        final List<String> test = Arrays.asList("Hello","There");

        Guard.checkArg(test)
                .describedAs("The argument")
                .is(not(empty()))
                .with("first result", Extract.<String>firstResult(), is(equalTo("Hello")))
                .thenReturnExpected();

    }

    @Test(expected = IllegalArgumentException.class)
    public void testFirstResultNoMatch() throws Exception {

        final List<String> test = Arrays.asList("Hello","There");

        Guard.checkArg(test)
                .describedAs("The value for 'list'")
                .is(not(empty()))
                .with("first result", Extract.<String>firstResult(), is(equalTo("There")))
                .thenReturnExpected();

    }

}
