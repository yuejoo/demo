package com.zhaoye.prodlinearity.factory.models;

import org.immutables.value.Value;

import java.util.LinkedList;

@Value.Immutable
public interface ProductionLineWithProduces extends ProductionLineWithDemands
{
    static ProductionLineWithProduces of(
        final ProductionLineWithDemands productionLineWithDemands,
        final LinkedList<Produce> produces
    )
    {
        return ImmutableProductionLineWithProduces.builder()
            .from(productionLineWithDemands)
            .produces(produces)
            .build();
    }

    LinkedList<Produce> produces();
}
