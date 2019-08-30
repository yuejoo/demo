package com.zhaoye.prodlinearity.helper;

import com.sun.tools.javac.util.Pair;
import com.zhaoye.prodlinearity.csv.models.CsvContainer;
import com.zhaoye.prodlinearity.csv.models.ImmutableCsvContainer;
import com.zhaoye.prodlinearity.csv.models.Pojo;
import com.zhaoye.prodlinearity.factory.models.Amount;
import com.zhaoye.prodlinearity.factory.models.Day;
import com.zhaoye.prodlinearity.factory.models.Demand;
import com.zhaoye.prodlinearity.factory.models.ImmutableProductionLine;
import com.zhaoye.prodlinearity.factory.models.ImmutableProductionLineWithDemands;
import com.zhaoye.prodlinearity.factory.models.ImmutableProductionLineWithProduces;
import com.zhaoye.prodlinearity.factory.models.KeyDayWithDemand;
import com.zhaoye.prodlinearity.factory.models.Produce;
import com.zhaoye.prodlinearity.factory.models.Product;
import com.zhaoye.prodlinearity.factory.models.ProductionLine;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;
import net.bytebuddy.utility.RandomString;

public class MockHelper
{
    public static ProductionLine generateProductionLine(
        final String productName,
        final int[] days,
        final int[][] keyDayWithDemands
    )
    {
        final LinkedList<Day> daysList = new LinkedList<>();
        final LinkedList<KeyDayWithDemand> keyDayWithDemandList = new LinkedList<>();
        for(final int[] pair : keyDayWithDemands)
        {
            keyDayWithDemandList.add(
                KeyDayWithDemand.of(Day.of(pair[0]), Demand.of(Amount.of(pair[1])))
            );
        }
        for(final int day : days)
        {
            daysList.add(Day.of(day));
        }

        return ImmutableProductionLine.builder()
            .product(Product.of(productName))
            .keyDayWithDemands(keyDayWithDemandList)
            .days(daysList)
            .build();
    }

    public static ProductionLine generateProductionLineWithDemands(
        final String productName,
        final int[] days,
        final int[][] keyDayWithDemands,
        final int[] demands
    )
    {
        final LinkedList<Demand> demandsList = new LinkedList<>();

        for(final int demand : demands)
        {
            demandsList.add(Demand.of(Amount.of(demand)));
        }

        return ImmutableProductionLineWithDemands.builder()
            .from(generateProductionLine(productName, days, keyDayWithDemands))
            .demands(demandsList)
            .build();
    }

    public static ProductionLine generateProductionLineWithProduce(
        final String productName,
        final int[] days,
        final int[][] keyDayWithDemands,
        final int[] demands,
        final int[] produces
    )
    {
        final LinkedList<Produce> producesList = new LinkedList<>();

        for(final int produce : produces)
        {
            producesList.add(Produce.of(Amount.of(produce)));
        }

        return ImmutableProductionLineWithProduces.builder()
            .from(generateProductionLineWithDemands(productName, days, keyDayWithDemands, demands))
            .produces(producesList)
            .build();
    }

    public static Object[] mockPojoExtractorTestObjectPair()
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

    private static final Random RANDOM = new Random();
    private static final RandomString RANDOM_STRING = new RandomString(4);
}
