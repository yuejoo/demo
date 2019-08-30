package com.zhaoye.prodlinearity.csv.impl;

import com.zhaoye.prodlinearity.csv.PojoCreator;
import com.zhaoye.prodlinearity.csv.models.OutputPojo;
import com.zhaoye.prodlinearity.factory.models.Day;
import com.zhaoye.prodlinearity.factory.models.Demand;
import com.zhaoye.prodlinearity.factory.models.Produce;
import com.zhaoye.prodlinearity.factory.models.ProductionLineWithProduces;
import com.zhaoye.prodlinearity.factory.models.Site;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public final class DefaultPojoCreator implements PojoCreator
{
    @Override
    public List<OutputPojo> create(final Collection<Site> siteList)
    {
        final List<OutputPojo> pojos = new LinkedList<>();
        for(final Site site : siteList)
        {
            for (final ProductionLineWithProduces productionLineWithProduces : site.productionLines())
            {
                final ListIterator<Day> dayListIterator =
                    productionLineWithProduces.days().listIterator();
                final ListIterator<Demand> demandListIterator =
                    productionLineWithProduces.demands().listIterator();
                final ListIterator<Produce> produceListIterator =
                    productionLineWithProduces.produces().listIterator();

                while(dayListIterator.hasNext())
                {
                    pojos.add(
                        new OutputPojo(
                            site.name(),
                            productionLineWithProduces.product().value(),
                            dayListIterator.next().value(),
                            demandListIterator.next().amount().value(),
                            produceListIterator.next().amount().value()
                        )
                    );
                }
            }
        }
        return pojos;
    }
}
