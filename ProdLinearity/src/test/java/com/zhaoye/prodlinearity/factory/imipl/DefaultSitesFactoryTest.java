package com.zhaoye.prodlinearity.factory.imipl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.sun.tools.javac.util.Pair;
import com.zhaoye.prodlinearity.csv.models.CsvContainer;
import com.zhaoye.prodlinearity.factory.ProductionLinesFactory;
import com.zhaoye.prodlinearity.factory.SitesFactory;
import com.zhaoye.prodlinearity.factory.impl.DefaultSitesFactory;
import com.zhaoye.prodlinearity.factory.models.ImmutableSite;
import com.zhaoye.prodlinearity.factory.models.ProductionLine;
import com.zhaoye.prodlinearity.factory.models.ProductionLineWithDemands;
import com.zhaoye.prodlinearity.factory.models.ProductionLineWithProduces;
import com.zhaoye.prodlinearity.factory.models.Site;
import com.zhaoye.prodlinearity.helper.MockHelper;
import com.zhaoye.prodlinearity.planner.DemandPlanner;
import com.zhaoye.prodlinearity.planner.PreBuildDayProvider;
import com.zhaoye.prodlinearity.planner.ProducePlanner;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultSitesFactoryTest
{
    @Before
    public void initialize()
    {
        final Set<Map.Entry<String, Map<String, TreeSet<Pair<Integer, Integer>>>>> entrySet = new HashSet<>();
        entrySet.add(entry);
        Mockito.when(inputCsvContainer.value()).thenReturn(csvContainerValue);
        Mockito.when(csvContainerValue.entrySet()).thenReturn(entrySet);

        final int preBuildDay = MockHelper.RANDOM.nextInt(5);

        productionLineWithProducesList = Arrays.asList(productionLineWithProduce);

        Mockito.when(entry.getValue()).thenReturn(productToDayDemandsPairMap);
        Mockito.when(entry.getKey()).thenReturn(SITE_NAME);
        Mockito.when(productionLinesFactory.create(productToDayDemandsPairMap)).thenReturn(productionLines);
        Mockito.when(preBuildDayProvider.provide()).thenReturn(preBuildDay);
        Mockito.when(demandPlanner.plan(productionLines))
            .thenReturn(productionLineWithDemands);
        Mockito.when(producePlanner.plan(
            productionLineWithDemands, preBuildDay
        )).thenReturn(
            productionLineWithProducesList
        );

        this.sitesFactory = new DefaultSitesFactory(
            productionLinesFactory,
            demandPlanner,
            producePlanner,
            preBuildDayProvider
        );
    }

    @Test
    public void testCreate()
    {
        assertThat(
            sitesFactory.create(inputCsvContainer),
            is(
                Arrays.asList(
                    ImmutableSite.builder()
                       .name(SITE_NAME)
                       .productionLines(Arrays.asList(productionLineWithProduce))
                       .build()
                )
            )
        );
    }

    @Test
    public void testCreateWithEmptyCsvContainer()
    {
        Mockito.when(inputCsvContainer.value()).thenReturn(Collections.emptyMap());
        assertThat(
            sitesFactory.create(inputCsvContainer),
            is(Collections.emptyList())
        );
    }

    private SitesFactory sitesFactory;
    private List<ProductionLineWithProduces> productionLineWithProducesList;

    private static String SITE_NAME = MockHelper.RANDOM_STRING.nextString();

    @Mock private CsvContainer inputCsvContainer;
    @Mock private List<Site> expectedSites;

    @Mock private ProductionLinesFactory productionLinesFactory;
    @Mock private DemandPlanner demandPlanner;
    @Mock private ProducePlanner producePlanner;
    @Mock private PreBuildDayProvider preBuildDayProvider;
    @Mock private Map csvContainerValue;
    @Mock private Map.Entry<String, Map<String, TreeSet<Pair<Integer, Integer>>>> entry;
    @Mock private Map<String, TreeSet<Pair<Integer, Integer>>> productToDayDemandsPairMap;

    @Mock private List<ProductionLine> productionLines;
    @Mock private List<ProductionLineWithDemands> productionLineWithDemands;
    @Mock private ProductionLineWithProduces productionLineWithProduce;
}
