package com.zhaoye.prodlinearity.factory.models;

import org.immutables.value.Value;

import java.util.LinkedList;

@Value.Immutable
public interface ProductionLineWithDemands extends ProductionLine
{
    static ProductionLineWithDemands of(
        final ProductionLine productionLine,
        final LinkedList<Demand> demands
    )
    {
        return ImmutableProductionLineWithDemands.builder()
            .from(productionLine)
            .demands(demands)
            .build();
    }

    LinkedList<Demand> demands();
}
