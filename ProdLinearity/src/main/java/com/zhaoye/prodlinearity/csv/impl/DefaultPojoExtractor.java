package com.zhaoye.prodlinearity.csv.impl;

import com.sun.tools.javac.util.Pair;
import com.zhaoye.prodlinearity.csv.PojoExtractor;
import com.zhaoye.prodlinearity.csv.models.CsvContainer;
import com.zhaoye.prodlinearity.csv.models.ImmutableCsvContainer;
import com.zhaoye.prodlinearity.csv.models.Pojo;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public final class DefaultPojoExtractor implements PojoExtractor
{
    @Override
    public CsvContainer extract(final List<Pojo> pojos)
    {
        final Map<String, Map<String, TreeSet<Pair<Integer, Integer>>>>
            siteToProductToProductionLineMap = new HashMap<>();
        for(final Pojo pojo : pojos)
        {
            final String siteName = pojo.site;
            final String productName = pojo.product;
            final int demand = pojo.demand;
            final int day = pojo.day;

            siteToProductToProductionLineMap.putIfAbsent(siteName, new HashMap<>());
            siteToProductToProductionLineMap.get(siteName).putIfAbsent(
                productName,
                new TreeSet<>(Comparator.comparingInt(pair -> pair.fst))
            );
            siteToProductToProductionLineMap.get(siteName).get(productName)
                .add(new Pair<>(day, demand));
        }

        return ImmutableCsvContainer.builder()
            .value(siteToProductToProductionLineMap)
            .build();
    }
}