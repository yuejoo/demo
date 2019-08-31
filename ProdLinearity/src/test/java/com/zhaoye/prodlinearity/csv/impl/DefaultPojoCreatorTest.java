package com.zhaoye.prodlinearity.csv.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.zhaoye.prodlinearity.csv.PojoCreator;
import com.zhaoye.prodlinearity.csv.models.OutputPojo;
import com.zhaoye.prodlinearity.factory.ProgressBarFactory;
import com.zhaoye.prodlinearity.factory.models.ImmutableSite;
import com.zhaoye.prodlinearity.factory.models.ProductionLineWithProduces;
import com.zhaoye.prodlinearity.factory.models.Site;
import com.zhaoye.prodlinearity.helper.MockHelper;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import me.tongfei.progressbar.ProgressBar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.Mockito;


@RunWith(Parameterized.class)
public final class DefaultPojoCreatorTest
{
    @Before
    public void initialize() {
        this.pojoCreator = new DefaultPojoCreator(progressBarFactory);
    }

    @Parameters
    public static Collection getTestParameters()
    {
        final String siteName = "Site";
        final String productName = "Product";

        final ProductionLineWithProduces productionLineWithProduces =
            MockHelper.generateProductionLineWithProduce(
                productName,
                new int[]{1, 2, 3}, // days
                new int[][]{}, // keyDayDemands
                new int[]{0, 200, 300}, // demand
                new int[]{100, 100, 200} // produce
            );

        final Site site = ImmutableSite.builder()
            .name(siteName)
            .productionLines(Arrays.asList(productionLineWithProduces))
            .build();

        return Arrays.asList(
            new Object[][] {
                {Collections.emptySet(), Collections.emptyList()},
                {
                    Arrays.asList(site),
                    Arrays.asList(
                        new OutputPojo(siteName, productName, 1, 0, 100),
                        new OutputPojo(siteName, productName, 2, 200, 100),
                        new OutputPojo(siteName, productName, 3, 300, 200)
                    ).stream().collect(Collectors.toCollection(LinkedList::new))
                }
            });
    }

    @Test
    public void testCreate()
    {
        Mockito.when(progressBarFactory.get("Persisting:", inputSiteList.size())).thenReturn(progressBar);
        final ListIterator<OutputPojo> resultIterator = pojoCreator.create(inputSiteList).listIterator();
        final ListIterator<OutputPojo> expectedIterator = expectedResult.listIterator();

        while(resultIterator.hasNext() && expectedIterator.hasNext())
        {
            final OutputPojo result = resultIterator.next();
            final OutputPojo expect = expectedIterator.next();

            assertThat(result.site, is(expect.site));
            assertThat(result.product, is(expect.product));
            assertThat(result.produce, is(expect.produce));
            assertThat(result.demand, is(expect.demand));
            assertThat(result.day, is(expect.day));
        }

        assertThat(resultIterator.hasNext(), is(expectedIterator.hasNext()));
        Mockito.verify(progressBar, Mockito.times(inputSiteList.size())).step();
    }

    public DefaultPojoCreatorTest(
        final Collection<Site> inputSiteList,
        final List<OutputPojo> expectedResult
    )
    {
        this.inputSiteList = inputSiteList;
        this.expectedResult = expectedResult;
    }

    private ProgressBarFactory progressBarFactory = Mockito.mock(ProgressBarFactory.class);
    private ProgressBar progressBar = Mockito.mock(ProgressBar.class);
    private List<OutputPojo> expectedResult;
    private Collection<Site> inputSiteList;
    private PojoCreator pojoCreator;
}