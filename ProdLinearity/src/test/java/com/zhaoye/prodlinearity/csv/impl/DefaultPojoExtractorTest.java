package com.zhaoye.prodlinearity.csv.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.zhaoye.prodlinearity.csv.PojoExtractor;
import com.zhaoye.prodlinearity.csv.models.CsvContainer;
import com.zhaoye.prodlinearity.csv.models.ImmutableCsvContainer;
import com.zhaoye.prodlinearity.csv.models.Pojo;
import com.zhaoye.prodlinearity.helper.MockHelper;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public final class DefaultPojoExtractorTest
{
    @Before
    public void initialize() {
        this.pojoExtractor = new DefaultPojoExtractor();
    }

    @Parameters
    public static Collection getTestParameters() {
        final List<Pojo> emptyList = Collections.EMPTY_LIST;
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
        assertThat(
            pojoExtractor.extract(inputPojos),
            is(expectedResult)
        );
    }

    public DefaultPojoExtractorTest(
        final List<Pojo> inputPojos,
        final CsvContainer expectedResult
    )
    {
        this.inputPojos = inputPojos;
        this.expectedResult = expectedResult;
    }

    private List<Pojo> inputPojos;
    private CsvContainer expectedResult;
    private PojoExtractor pojoExtractor;
}
