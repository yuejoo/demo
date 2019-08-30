package com.zhaoye.prodlinearity.helper;

import com.sun.tools.javac.util.Pair;
import com.zhaoye.prodlinearity.csv.models.CsvContainer;
import com.zhaoye.prodlinearity.csv.models.ImmutableCsvContainer;
import com.zhaoye.prodlinearity.csv.models.InputPojo;
import com.zhaoye.prodlinearity.factory.models.Amount;
import com.zhaoye.prodlinearity.factory.models.Day;
import com.zhaoye.prodlinearity.factory.models.Demand;
import com.zhaoye.prodlinearity.factory.models.ImmutableProductionLine;
import com.zhaoye.prodlinearity.factory.models.ImmutableProductionLineWithDemands;
import com.zhaoye.prodlinearity.factory.models.ImmutableProductionLineWithProduces;
import com.zhaoye.prodlinearity.factory.models.ImmutableSite;
import com.zhaoye.prodlinearity.factory.models.KeyDayWithDemand;
import com.zhaoye.prodlinearity.factory.models.Produce;
import com.zhaoye.prodlinearity.factory.models.Product;
import com.zhaoye.prodlinearity.factory.models.ProductionLine;
import com.zhaoye.prodlinearity.factory.models.ProductionLineWithProduces;
import com.zhaoye.prodlinearity.factory.models.Site;
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
    public static Map<String, TreeSet<Pair<Integer, Integer>>>  createSingleProductToKeyDayDemandsPairMap(
        final String productName,
        final int[] days,
        final int[][] keyDaysWithDemands
    )
    {
        final Map<Integer, Integer> keyDaysWithDemandsMap = new HashMap<>();
        for(int[] keyDayWithDemand : keyDaysWithDemands) keyDaysWithDemandsMap.put(keyDayWithDemand[0], keyDayWithDemand[1]);
        final Map<String, TreeSet<Pair<Integer, Integer>>> mockMap = new HashMap<>();
        final TreeSet<Pair<Integer, Integer>> mockSet = new TreeSet<>(Comparator.comparingInt(p -> p.fst));
        for(int day : days)
        {
            if(keyDaysWithDemandsMap.containsKey(day)) mockSet.add(new Pair<>(day, keyDaysWithDemandsMap.get(day)));
            else mockSet.add(new Pair<>(day, 0));
        }
        mockMap.put(productName, mockSet);
        return mockMap;
    }

    public static Site generateSite(
        final String siteName,
        final List<ProductionLineWithProduces> productionLineWithProduces
    )
    {
        return ImmutableSite.builder()
            .name(siteName)
            .productionLines(productionLineWithProduces)
            .build();
    }

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

    public static ProductionLineWithProduces generateProductionLineWithProduce(
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
        final List<InputPojo> inputPojoList = new LinkedList<>();

        final int numberOfEntries = RANDOM.nextInt(10);
        for(int i = 0; i < numberOfEntries; i++)
        {
            final String site = RANDOM_STRING.nextString();
            final String product = RANDOM_STRING.nextString();
            final int day = RANDOM.nextInt();
            final int demand = RANDOM.nextInt();
            inputPojoList.add(mockPojo(site, product, day, demand));
            addContentToCsvContainerData(
                csvContainerValue,
                site,
                product,
                day,
                demand
            );
        }

        pair[0] = inputPojoList;
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

    private static InputPojo mockPojo(final String site, final String product, final int day, final int demand)
    {
        final InputPojo inputPojo = new InputPojo();
        inputPojo.site = site;
        inputPojo.day = day;
        inputPojo.demand = demand;
        inputPojo.product = product;
        return inputPojo;
    }

    public static final Random RANDOM = new Random();
    public static final RandomString RANDOM_STRING = new RandomString(4);
}
