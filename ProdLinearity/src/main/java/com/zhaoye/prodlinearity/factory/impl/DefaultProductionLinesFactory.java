package com.zhaoye.prodlinearity.factory.impl;

import com.sun.tools.javac.util.Pair;
import com.zhaoye.prodlinearity.factory.ProductionLinesFactory;
import com.zhaoye.prodlinearity.factory.models.Amount;
import com.zhaoye.prodlinearity.factory.models.Day;
import com.zhaoye.prodlinearity.factory.models.Demand;
import com.zhaoye.prodlinearity.factory.models.ImmutableProductionLine;
import com.zhaoye.prodlinearity.factory.models.KeyDayWithDemand;
import com.zhaoye.prodlinearity.factory.models.Product;
import com.zhaoye.prodlinearity.factory.models.ProductionLine;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public final class DefaultProductionLinesFactory implements ProductionLinesFactory
{
    @Override
    public List<ProductionLine> create(
        final Map<String, TreeSet<Pair<Integer, Integer>>> productToDayDemandPairMap
    )
    {
        return productToDayDemandPairMap.entrySet().stream().map(this::createProductionLine)
            .collect(Collectors.toList());
    }

    private ProductionLine createProductionLine(
        final Map.Entry<String, TreeSet<Pair<Integer, Integer>>> productToDayDemandPairMap
    )
    {
        final TreeSet<Pair<Integer, Integer>> dayDemandPairs = productToDayDemandPairMap.getValue();
        final Product product = Product.of(productToDayDemandPairMap.getKey());

        final LinkedList<Day> days = new LinkedList<>();
        final LinkedList<KeyDayWithDemand> keyDayWithDemands = new LinkedList<>();

        for(final Pair<Integer, Integer> pair : dayDemandPairs)
        {
            final int dayValue = pair.fst, demandValue = pair.snd;
            final Day day = Day.of(dayValue);
            days.add(day);
            // Identified the KeyDay with its demand
            if(demandValue > 0) keyDayWithDemands.add(
                KeyDayWithDemand.of(
                    day, Demand.of(Amount.of(demandValue))
                )
            );
        }

        return ImmutableProductionLine.builder()
            .days(days)
            .product(product)
            .keyDayWithDemands(keyDayWithDemands)
            .build();
    }
}
