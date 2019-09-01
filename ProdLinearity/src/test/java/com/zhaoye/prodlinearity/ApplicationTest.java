package com.zhaoye.prodlinearity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import picocli.CommandLine;
import picocli.CommandLine.ExitCode;

/**
 * Integration test for Application.
 */
@RunWith(MockitoJUnitRunner.class)
public final class ApplicationTest
{
    /**
     * Integration Test :-)
     */
    @Test
    public void testWithHeader() throws IOException
    {
        final String input = "src/test/resource/input.csv";
        final String result = "src/test/resource/output.csv";
        final String expected = "src/test/resource/expected-3.csv";
        final String preBuildDay = "3";

        int exitCode = new CommandLine(new Application())
            .execute(
                new String[]{"-i", input, "-o", result, "-p", preBuildDay}
            );

        assertThat(exitCode, is(ExitCode.OK));
        compareFiles(result, expected);
    }

    @Test
    public void testWithRandomOrder() throws IOException
    {
        final String input = "src/test/resource/inputWithRandomOrder.csv";
        final String result = "src/test/resource/output.csv";
        final String expected = "src/test/resource/expected-3.csv";
        final String preBuildDay = "3";

        int exitCode = new CommandLine(new Application())
            .execute(
                new String[]{"-i", input, "-o", result, "-p", preBuildDay}
            );

        assertThat(exitCode, is(ExitCode.OK));
        compareFiles(result, expected);
    }

    @Test
    public void testWithOutHeader() throws IOException
    {
        final String input = "src/test/resource/inputWithOutHeader.csv";
        final String result = "src/test/resource/output.csv";
        final String expected = "src/test/resource/expected-3.csv";
        final String preBuildDay = "3";

        int exitCode = new CommandLine(new Application())
            .execute(
                new String[]{"-i", input, "-o", result, "-p", preBuildDay, "--withoutHeader"}
            );

        assertThat(exitCode, is(ExitCode.OK));
        compareFiles(result, expected);
    }

    @Test
    public void testWith6PreBuildDays() throws IOException
    {
        final String input = "src/test/resource/input.csv";
        final String result = "src/test/resource/output.csv";
        final String expected = "src/test/resource/expected-6.csv";
        final String preBuildDay = "6";

        int exitCode = new CommandLine(new Application())
            .execute(
                new String[]{"-i", input, "-o", result, "-p", preBuildDay}
            );

        assertThat(exitCode, is(ExitCode.OK));
        compareFiles(result, expected);
    }

    @Test
    public void testWithException()
    {
        final String input = "src/test/resource/invalidInput.csv";
        final String result = "src/test/resource/output.csv";
        final String preBuildDay = "6";

        int exitCode = new CommandLine(new Application())
            .execute(
                new String[]{"-i", input, "-o", result, "-p", preBuildDay}
            );

        assertThat(exitCode, is(ExitCode.USAGE));
    }

    public void compareFiles(final String result, final String expected) throws IOException
    {
        final File actualFile = new File(result);
        final File expectedFile = new File(expected);
        assertThat(actualFile.exists(), is(true));
        assertThat(
            FileUtils.readFileToString(actualFile, Charset.defaultCharset()),
            is(FileUtils.readFileToString(expectedFile, Charset.defaultCharset()))
        );
    }
}
