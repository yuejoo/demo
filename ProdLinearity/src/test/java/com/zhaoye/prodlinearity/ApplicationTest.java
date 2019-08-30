package com.zhaoye.prodlinearity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import picocli.CommandLine;

/**
 * Unit test for simple Application.
 */
@RunWith(MockitoJUnitRunner.class)
public final class ApplicationTest
{
    /**
     * Rigorous Test :-)
     */
    @Ignore
    @Test
    public void testApplicationCall()
    {
        Application application = new Application();
        assertThat(application.call(), is(CommandLine.ExitCode.OK));
    }
}
