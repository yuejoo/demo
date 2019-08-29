package com.zhaoye.prodlinearity.csv.models;

import com.sun.tools.javac.util.Pair;
import java.util.Map;
import java.util.TreeSet;
import org.immutables.value.Value;

@Value.Immutable
public interface CsvContainer
{
    Map<String, Map<String, TreeSet<Pair<Integer, Integer>>>> value();
}
