package com.zhaoye.prodlinearity.csv.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Input Csv file lines POJO.
 */
@JsonPropertyOrder({ "site", "product", "day", "demand" })
public class Pojo
{
    public String site;
    public String product;
    public int day;
    public int demand;
}