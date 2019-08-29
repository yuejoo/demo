package com.zhaoye.prodlinearity.csv.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.sun.tools.javac.util.Pair;
import com.zhaoye.prodlinearity.csv.PojoExtractor;
import com.zhaoye.prodlinearity.csv.models.CsvContainer;
import com.zhaoye.prodlinearity.csv.models.ImmutableCsvContainer;
import com.zhaoye.prodlinearity.csv.models.Pojo;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;
import net.bytebuddy.utility.RandomString;
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
                mockObjectPair()
        });
    }

    @Test
    public void testExtract()
    {
        assertThat(pojoExtractor.extract(inputPojos).equals(expectedResult), is(true));
    }

    private static Object[] mockObjectPair()
    {
        final Object[] pair = new Object[2];
        final Map csvContainerValue = new HashMap();
        final List<Pojo> pojoList = new LinkedList<>();

        final int numberOfEntries = RANDOM.nextInt(10);
        for(int i = 0; i < numberOfEntries; i++)
        {
            final String site = RANDOM_STRING.nextString();
            final String product = RANDOM_STRING.nextString();
            final int day = RANDOM.nextInt();
            final int demand = RANDOM.nextInt();
            pojoList.add(mockPojo(site, product, day, demand));
            addContentToCsvContainerData(
                csvContainerValue,
                site,
                product,
                day,
                demand
            );
        }

        pair[0] = pojoList;
        pair[1] = mockCsvContainer(csvContainerValue);
        return pair;
    }

    private static CsvContainer mockCsvContainer(Map csvContainerValue)
    {
        return ImmutableCsvContainer.builder().value(csvContainerValue).build();
    }

    private static void addContentToCsvContainerData(
        final Map<String, Map<String, TreeSet<Pair<Integer, Integer>>>> csvContainerValue,
        final String site,
        final String product,
        final int day,
        final int demand
    )
    {
        csvContainerValue.putIfAbsent(site, new HashMap<>());
        csvContainerValue.get(site).putIfAbsent(product, new TreeSet<>(Comparator.comparingInt(p -> p.fst)));
        csvContainerValue.get(site).get(product).add(new Pair<>(day, demand));
    }

    private static Pojo mockPojo(final String site, final String product, final int day, final int demand)
    {
        final Pojo pojo = new Pojo();
        pojo.site = site;
        pojo.day = day;
        pojo.demand = demand;
        pojo.product = product;
        return pojo;
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

    private static final Random RANDOM = new Random();
    private static final RandomString RANDOM_STRING = new RandomString(4);
}
