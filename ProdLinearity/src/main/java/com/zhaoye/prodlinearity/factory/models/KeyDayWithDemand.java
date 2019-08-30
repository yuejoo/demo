package com.zhaoye.prodlinearity.factory.models;


import org.immutables.value.Value;

@Value.Immutable
public interface KeyDayWithDemand
{
    static KeyDayWithDemand of(final Day day, final Demand demand)
    {
        return ImmutableKeyDayWithDemand.builder()
            .day(day)
            .demand(demand)
            .build();
    }

    Day day();
    Demand demand();
}