package com.zhaoye.prodlinearity.csv;

import com.zhaoye.prodlinearity.csv.models.CsvContainer;
import com.zhaoye.prodlinearity.csv.models.InputPojo;
import java.util.List;

/**
 * Extract data from pojo class to CsvContainer.
 */
public interface PojoExtractor
{
    CsvContainer extract(final List<InputPojo> inputPojoList);
}
