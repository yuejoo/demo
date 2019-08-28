package com.zhaoye.prodlinearity.models;


import org.immutables.value.Value;

@Value.Immutable
public interface KeyDayWithDemand
{
    Day day();
    Demand demand();
}