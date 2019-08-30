package com.zhaoye.prodlinearity.factory.models;

import org.immutables.value.Value;

@Value.Immutable
public interface Demand
{
    static Demand of(final Amount amount)
    {
        return ImmutableDemand.builder().amount(amount).build();
    }

    Amount amount();
}