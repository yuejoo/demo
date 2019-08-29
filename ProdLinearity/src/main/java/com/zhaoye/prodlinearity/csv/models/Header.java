package com.zhaoye.prodlinearity.csv.models;

/**
 * Csv Headers
 */
public enum Header
{
    SITE(0, "site"),
    PRODUCT(1,"product"),
    DAY(2, "day"),
    DEMAND(3, "demand"),
    PRODUCE(4, "produce");

    private final int index;
    private final String name;

    public int getIndex()
    {
        return index;
    }

    public String getName()
    {
        return name;
    }

    private Header(int index, String name)
    {
        this.index = index;
        this.name = name;
    }
}
