package com.zhaoye.prodlinearity.factory;

import com.sun.tools.javac.util.Pair;
import com.zhaoye.prodlinearity.factory.models.ProductionLine;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Interface of Factory to create initial ProductionLine of a Site.
 */
public interface ProductionLinesFactory
{
    List<ProductionLine> create(
        final Map<String, TreeSet<Pair<Integer, Integer>>> productToDayDemandPairMap
    );
}
