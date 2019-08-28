package com.zhaoye.prodlinearity.models;

import org.immutables.value.Value;

import java.util.LinkedList;

@Value.Immutable
public interface ProductionLine
{
    Product product();
    LinkedList<KeyDayWithDemand> keyDayWithDemands();
    LinkedList<Day> days();
}
