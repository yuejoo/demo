package com.zhaoye.prodlinearity.planner.impl;

import com.zhaoye.prodlinearity.factory.models.Amount;
import com.zhaoye.prodlinearity.factory.models.Day;
import com.zhaoye.prodlinearity.factory.models.Demand;
import com.zhaoye.prodlinearity.factory.models.KeyDayWithDemand;
import com.zhaoye.prodlinearity.factory.models.ProductionLine;
import com.zhaoye.prodlinearity.factory.models.ProductionLineWithDemands;
import com.zhaoye.prodlinearity.planner.DemandPlanner;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public final class DefaultDemandPlanner implements DemandPlanner
{
    @Override
    public List<ProductionLineWithDemands> plan(final List<ProductionLine> productionLines)
    {
        return productionLines.stream()
            .map(this::planProductionLine)
            .collect(Collectors.toList());
    }

    private ProductionLineWithDemands planProductionLine(final ProductionLine productionLine)
    {
        final LinkedList<Day> days = productionLine.days();
        final LinkedList<KeyDayWithDemand> keyDayWithDemands = productionLine.keyDayWithDemands();

        final ListIterator<Day> daysIterator = days.listIterator(days.size());
        final ListIterator<KeyDayWithDemand> keyDayWithDemandsIterator =
            keyDayWithDemands.listIterator(keyDayWithDemands.size());

        final LinkedList<Demand> demands = new LinkedList<>();

        while(daysIterator.hasPrevious())
        {
            final Day currentDay = daysIterator.previous();
            Demand demand = EMPTY_DEMAND;
            if(keyDayWithDemandsIterator.hasPrevious())
            {
                KeyDayWithDemand mostRecentKeyDay = keyDayWithDemandsIterator.previous();
                if(mostRecentKeyDay.day().value() < currentDay.value())
                {
                    keyDayWithDemandsIterator.next();
                }
                demand = mostRecentKeyDay.demand();
            }
            demands.addFirst(demand);
        }

        return ProductionLineWithDemands.of(
            productionLine,
            demands
        );
    }

    private static final Demand EMPTY_DEMAND = Demand.of(Amount.of(0));
}
