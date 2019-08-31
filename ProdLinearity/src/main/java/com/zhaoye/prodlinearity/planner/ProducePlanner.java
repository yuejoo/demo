package com.zhaoye.prodlinearity.planner;

import com.zhaoye.prodlinearity.factory.models.ProductionLineWithDemands;
import com.zhaoye.prodlinearity.factory.models.ProductionLineWithProduces;
import java.util.Collection;
import java.util.List;

public interface ProducePlanner
{
    Collection<ProductionLineWithProduces> plan(
        final List<ProductionLineWithDemands> productionLineWithDemands,
        final int preBuildDays
    );
}
