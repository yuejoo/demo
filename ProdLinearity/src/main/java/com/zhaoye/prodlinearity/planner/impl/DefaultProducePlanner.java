package com.zhaoye.prodlinearity.planner.impl;

import com.zhaoye.prodlinearity.factory.models.Amount;
import com.zhaoye.prodlinearity.factory.models.Day;
import com.zhaoye.prodlinearity.factory.models.Demand;
import com.zhaoye.prodlinearity.factory.models.ImmutableProductionLineWithProduces;
import com.zhaoye.prodlinearity.factory.models.KeyDayWithDemand;
import com.zhaoye.prodlinearity.factory.models.Produce;
import com.zhaoye.prodlinearity.factory.models.ProductionLineWithDemands;
import com.zhaoye.prodlinearity.factory.models.ProductionLineWithProduces;
import com.zhaoye.prodlinearity.planner.ProducePlanner;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeSet;
import java.util.stream.Collectors;

public final class DefaultProducePlanner implements ProducePlanner
{
    @Override
    public Collection<ProductionLineWithProduces> plan(
        final List<ProductionLineWithDemands> productionLineWithDemands,
        final int preBuildDays
    )
    {
        return productionLineWithDemands.stream().map(pld -> planProduces(pld, preBuildDays))
            .collect(Collectors.toCollection(() ->
                // Sorting by ProductName.
                new TreeSet<>(Comparator.comparing(pl->pl.product().value())))
            );
    }

    /**
     * More details for planning logic is in https://github.com/yuejoo/demo/blob/master/docs/PlanningLogic.md
     */
    private ProductionLineWithProduces planProduces(
        final ProductionLineWithDemands productionLineWithDemands,
        final int preBuildDays
    )
    {
        final LinkedList<Produce> produces = new LinkedList<>();
        final LinkedList<Demand> demands = productionLineWithDemands.demands();
        final LinkedList<Day> days = productionLineWithDemands.days();
        final LinkedList<KeyDayWithDemand> keyDayWithDemands = productionLineWithDemands.keyDayWithDemands();

        final ListIterator<Demand> demandsIterator = demands.listIterator();
        final ListIterator<Day> daysIterator = days.listIterator();

        final ListIterator<KeyDayWithDemand> keyDayWithDemandsIterator =
            keyDayWithDemands.listIterator();

        final LinkedList<Produce> queue = new LinkedList<>();

        while(daysIterator.hasNext())
        {
            final Day currentDay = daysIterator.next();
            final Demand currentDemand = demandsIterator.next();
            final Produce produce = Produce.of(currentDemand.amount());

            if(keyDayWithDemandsIterator.hasNext())
            {
                planningForPreDays(
                    queue,
                    keyDayWithDemandsIterator,
                    currentDay,
                    produce,
                    produces,
                    preBuildDays
                );
            }
            else // If there is no KeyDaysAfter
            {
                produces.add(produce);
            }
        }

        return ImmutableProductionLineWithProduces
            .builder()
            .from(productionLineWithDemands)
            .produces(produces)
            .build();
    }

    private void planningForPreDays(
        final LinkedList<Produce> queue,
        final ListIterator<KeyDayWithDemand> keyDayWithDemandsIterator,
        final Day currentDay,
        final Produce estimateProduce,
        final LinkedList<Produce> plannedProduces,
        final int preBuildDays
    )
    {
        final KeyDayWithDemand mostRecentKeyDayWithDemand = keyDayWithDemandsIterator.next();
        if(currentDay.value() < mostRecentKeyDayWithDemand.day().value())
        {
            queue.add(estimateProduce);
            keyDayWithDemandsIterator.previous();
        }
        else {
            // Skipping the non-pre build days.
            while (queue.size() > preBuildDays) {
                plannedProduces.add(queue.pollFirst());
            }
            // Planning for Prebuild days.
            while (!queue.isEmpty()) {
                int previousAmount = plannedProduces.isEmpty() ? 0 : plannedProduces.getLast().amount().value();
                int amount = (estimateProduce.amount().value() - previousAmount)/(queue.size() + 1);
                plannedProduces.add(
                    Produce.of(Amount.of(previousAmount + amount))
                );
                queue.pollFirst();
            }
            // Add the KeyDay
            plannedProduces.add(estimateProduce);
        }
    }
}
