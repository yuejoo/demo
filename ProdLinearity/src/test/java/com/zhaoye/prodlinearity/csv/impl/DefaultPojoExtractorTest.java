package com.zhaoye.prodlinearity.csv.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.zhaoye.prodlinearity.csv.PojoExtractor;
import com.zhaoye.prodlinearity.csv.models.CsvContainer;
import com.zhaoye.prodlinearity.csv.models.ImmutableCsvContainer;
import com.zhaoye.prodlinearity.csv.models.InputPojo;
import com.zhaoye.prodlinearity.factory.ProgressBarFactory;
import com.zhaoye.prodlinearity.helper.MockHelper;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import me.tongfei.progressbar.ProgressBar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

@RunWith(Parameterized.class)
public final class DefaultPojoExtractorTest
{
    @Before
    public void initialize() {
        this.pojoExtractor = new DefaultPojoExtractor(progressBarFactory);
    }

    @Parameters
    public static Collection getTestParameters() {
        final List<InputPojo> emptyList = Collections.EMPTY_LIST;
        final CsvContainer emptyCsvContainer =
            ImmutableCsvContainer.builder().value(Collections.EMPTY_MAP).build();

        return Arrays.asList(
            new Object[][] {
                {emptyList, emptyCsvContainer},
                MockHelper.mockPojoExtractorTestObjectPair()
        });
    }

    @Test
    public void testExtract()
    {
        Mockito.when(progressBarFactory.get("Parsing:", inputInputPojos.size())).thenReturn(progressBar);

        assertThat(
            pojoExtractor.extract(inputInputPojos),
            is(expectedResult)
        );
        Mockito.verify(progressBar, Mockito.times(inputInputPojos.size())).step();
    }

    public DefaultPojoExtractorTest(
        final List<InputPojo> inputInputPojos,
        final CsvContainer expectedResult
    )
    {
        this.inputInputPojos = inputInputPojos;
        this.expectedResult = expectedResult;
    }

    private List<InputPojo> inputInputPojos;
    private CsvContainer expectedResult;
    private PojoExtractor pojoExtractor;

    private ProgressBarFactory progressBarFactory = Mockito.mock(ProgressBarFactory.class);
    private ProgressBar progressBar = Mockito.mock(ProgressBar.class);
}
