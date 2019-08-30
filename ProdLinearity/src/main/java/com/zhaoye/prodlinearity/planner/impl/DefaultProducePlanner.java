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
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public final class DefaultProducePlanner implements ProducePlanner
{
    // TODO: Think about the non-raw days.
    @Override
    public List<ProductionLineWithProduces> plan(
        final List<ProductionLineWithDemands> productionLineWithDemands,
        final int preBuildDays
    )
    {
        return productionLineWithDemands.stream().map(pld -> planProduces(pld, preBuildDays))
            .collect(Collectors.toList());
    }

    private ProductionLineWithProduces planProduces(
        final ProductionLineWithDemands productionLineWithDemands,
        final int preBuildDays
    )
    {
        final LinkedList<Produce> produces = new LinkedList<>();
        final LinkedList<Demand> demands = productionLineWithDemands.demands();
        final LinkedList<Day> days = productionLineWithDemands.days();

        final ListIterator<Demand> demandsIterator = demands.listIterator();
        final ListIterator<Day> daysIterator = days.listIterator();

        final ListIterator<KeyDayWithDemand> keyDayWithDemandsIterator =
            productionLineWithDemands.keyDayWithDemands().listIterator();

        while(daysIterator.hasNext())
        {
            final Day currentDay = daysIterator.next();
            final Demand currentDemand = demandsIterator.next();
            Produce produce = Produce.of(currentDemand.amount());
            if(keyDayWithDemandsIterator.hasNext()) {
                KeyDayWithDemand mostRecentKeyDay = keyDayWithDemandsIterator.next();
                if(shouldPreProduce(mostRecentKeyDay, currentDay, preBuildDays))
                {
                    int previousAmount = produces.isEmpty() ? 0 : produces.getLast().amount().value();
                    int amount =
                        (mostRecentKeyDay.demand().amount().value() - previousAmount)/(mostRecentKeyDay.day().value() - currentDay.value() + 1);

                    produce = Produce.of(Amount.of(previousAmount + amount ));
                }

                if (mostRecentKeyDay.day().value() > currentDay.value())
                {
                    keyDayWithDemandsIterator.previous();
                }
            }

            produces.add(produce);
        }

        return ImmutableProductionLineWithProduces
            .builder()
            .from(productionLineWithDemands)
            .produces(produces)
            .build();
    }

    private boolean shouldPreProduce(
        final KeyDayWithDemand mostRecentKeyDay,
        final Day currentDay,
        final int preBuildDays
    )
    {
        return mostRecentKeyDay.day().value() - currentDay.value() <= preBuildDays
            &&
            mostRecentKeyDay.day().value() != currentDay.value();
    }
}
