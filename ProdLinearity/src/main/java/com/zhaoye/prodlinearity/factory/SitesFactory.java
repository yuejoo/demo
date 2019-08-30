package com.zhaoye.prodlinearity.factory;

import com.zhaoye.prodlinearity.csv.models.CsvContainer;
import com.zhaoye.prodlinearity.factory.models.Site;
import java.util.List;

/**
 * Interface to create the Sites from the CSV Parser Result.
 */
public interface SitesFactory
{
    List<Site> create(final CsvContainer csvContainer);
}
