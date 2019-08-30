package com.zhaoye.prodlinearity.csv.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Output Csv file lines POJO.
 */
@JsonPropertyOrder({ "site", "product", "day", "demand", "produce"})
public class OutputPojo
{
    public String site;
    public String product;
    public int day;
    public int demand;
    public int produce;

    public OutputPojo(
        final String site,
        final String product,
        final int day,
        final int demand,
        final int produce
    )
    {
        this.site = site;
        this.product = product;
        this.day = day;
        this.demand = demand;
        this.produce = produce;
    }
}