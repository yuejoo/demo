package com.zhaoye.prodlinearity.factory.impl;

import com.sun.tools.javac.util.Pair;
import com.zhaoye.prodlinearity.csv.models.CsvContainer;
import com.zhaoye.prodlinearity.factory.ProductionLinesFactory;
import com.zhaoye.prodlinearity.factory.SitesFactory;
import com.zhaoye.prodlinearity.factory.models.ImmutableSite;
import com.zhaoye.prodlinearity.factory.models.ProductionLine;
import com.zhaoye.prodlinearity.factory.models.ProductionLineWithDemands;
import com.zhaoye.prodlinearity.factory.models.ProductionLineWithProduces;
import com.zhaoye.prodlinearity.factory.models.Site;
import com.zhaoye.prodlinearity.planner.DemandPlanner;
import com.zhaoye.prodlinearity.planner.PreBuildDayProvider;
import com.zhaoye.prodlinearity.planner.ProducePlanner;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Sites factory to creating sites from CsvContainer with planing for its production lines.
 */
public final class DefaultSitesFactory implements SitesFactory
{
    @Override
    public Collection<Site> create(final CsvContainer csvContainer)
    {
        final TreeSet<Site> sites = new TreeSet<>(Comparator.comparing(s -> s.name()));

        for(final Map.Entry<String, Map<String, TreeSet<Pair<Integer, Integer>>>> entry : csvContainer.value().entrySet())
        {
            final String siteName = entry.getKey();
            final List<ProductionLine> productionLines = productionLinesFactory.create(entry.getValue());
            final List<ProductionLineWithDemands> productionLineWithDemands =
                demandPlanner.plan(productionLines);
            final Collection<ProductionLineWithProduces> productionLineWithProduces =
                producePlanner.plan(productionLineWithDemands, preBuildDayProvider.provide());

            sites.add(
                ImmutableSite.builder()
                    .name(siteName)
                    .productionLines(productionLineWithProduces)
                    .build()
            );
        }

        return sites;
    }

    public DefaultSitesFactory(
        final ProductionLinesFactory productionLinesFactory,
        final DemandPlanner demandPlanner,
        final ProducePlanner producePlanner,
        final PreBuildDayProvider preBuildDayProvider
    )
    {
        this.productionLinesFactory = productionLinesFactory;
        this.demandPlanner = demandPlanner;
        this.producePlanner = producePlanner;
        this.preBuildDayProvider = preBuildDayProvider;
    }

    private final ProductionLinesFactory productionLinesFactory;
    private final DemandPlanner demandPlanner;
    private final ProducePlanner producePlanner;
    private final PreBuildDayProvider preBuildDayProvider;
}
