package com.zhaoye.prodlinearity.factory.models;


import org.immutables.value.Value;

@Value.Immutable
public interface KeyDayWithDemand
{
    Day day();
    Demand demand();
}