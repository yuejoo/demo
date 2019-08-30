package com.zhaoye.prodlinearity.csv;

import com.zhaoye.prodlinearity.csv.models.OutputPojo;
import com.zhaoye.prodlinearity.factory.models.Site;
import java.util.Collection;
import java.util.List;

public interface PojoCreator
{
    List<OutputPojo> create(final Collection<Site> siteList);
}
