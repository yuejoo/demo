package com.zhaoye.prodlinearity.factory.models;


import org.immutables.value.Value;

@Value.Immutable
public interface Day
{
    static Day of(final int value)
    {
        return ImmutableDay.builder().value(value).build();
    }

    int value();
}
