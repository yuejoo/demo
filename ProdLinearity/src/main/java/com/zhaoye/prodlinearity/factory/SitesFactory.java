package com.zhaoye.prodlinearity.factory;

import com.zhaoye.prodlinearity.csv.models.CsvContainer;
import com.zhaoye.prodlinearity.factory.models.Site;
import java.util.Collection;

/**
 * Interface to create the Sites from the CSV Parser Result.
 */
public interface SitesFactory
{
    Collection<Site> create(final CsvContainer csvContainer);
}
