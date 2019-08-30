package com.zhaoye.prodlinearity.factory.models;


import org.immutables.value.Value;

@Value.Immutable
public interface Amount
{
    static Amount of(final int value)
    {
        return ImmutableAmount.builder().value(value).build();
    }

    int value();
}
