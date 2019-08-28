package com.zhaoye.prodlinearity.models;

import org.immutables.value.Value;

import java.util.LinkedList;

@Value.Immutable
public interface ProductionLineWithProduces extends ProductionLineWithDemands
{
    LinkedList<Produce> produces();
}
