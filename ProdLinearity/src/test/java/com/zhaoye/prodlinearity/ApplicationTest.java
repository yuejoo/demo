package com.zhaoye.prodlinearity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import picocli.CommandLine;

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
        assertThat(application.call(), is(CommandLine.ExitCode.OK));
    }
}
