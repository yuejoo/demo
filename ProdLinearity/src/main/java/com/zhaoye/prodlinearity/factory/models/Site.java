package com.zhaoye.prodlinearity.factory.models;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface Site
{
    String name();
    List<ProductionLineWithProduces> productionLines();
}