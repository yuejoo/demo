package com.zhaoye.prodlinearity.models;

import org.immutables.value.Value;

import java.util.LinkedList;

@Value.Immutable
public interface ProductionLineWithDemands extends ProductionLine
{
    LinkedList<Demand> demandPlans();
}
