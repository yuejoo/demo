package com.zhaoye.prodlinearity.csv;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.zhaoye.prodlinearity.csv.models.CsvContainer;
import java.io.File;
import java.io.IOException;

/**
 * CsvFiler Parser to parse the Csv file.
 */
public interface CsvFileParser
{
    CsvContainer parse(
        final File csvFile,
        final CsvSchema csvSchema
    ) throws IOException;
}
