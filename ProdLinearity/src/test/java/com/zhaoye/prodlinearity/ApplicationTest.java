package com.zhaoye.prodlinearity;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

/**
 * Unit test for simple Application.
 */
public final class ApplicationTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testApplicationCall()
    {
        Application application = new Application();
        assertThat(application.call(), nullValue());
    }
}
