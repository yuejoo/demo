package com.zhaoye.prodlinearity.planner;

import com.zhaoye.prodlinearity.factory.models.ProductionLine;
import com.zhaoye.prodlinearity.factory.models.ProductionLineWithDemands;
import java.util.List;

public interface DemandPlanner
{
    List<ProductionLineWithDemands> plan(
        final List<ProductionLine> productionLines
    );
}
