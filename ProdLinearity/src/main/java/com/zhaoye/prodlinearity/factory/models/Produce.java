package com.zhaoye.prodlinearity.factory.models;

import org.immutables.value.Value;

@Value.Immutable
public interface Produce
{
    static Produce of(final Amount amount)
    {
        return ImmutableProduce.builder().amount(amount).build();
    }

    Amount amount();
}
