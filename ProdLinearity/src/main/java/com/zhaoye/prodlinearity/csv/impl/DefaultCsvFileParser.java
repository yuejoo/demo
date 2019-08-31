package com.zhaoye.prodlinearity.csv.impl;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.zhaoye.prodlinearity.csv.CsvFileParser;
import com.zhaoye.prodlinearity.csv.PojoExtractor;
import com.zhaoye.prodlinearity.csv.models.InputPojo;
import com.zhaoye.prodlinearity.csv.models.CsvContainer;
import com.zhaoye.prodlinearity.csv.models.Header;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Extract the CsvContainer from the CsvFile.
 */
public final class DefaultCsvFileParser implements CsvFileParser
{
    @Override
    public CsvContainer parse(final File csvFile) throws IOException
    {
        System.out.println("Loading Csv File...");
        final MappingIterator<InputPojo> iterator = mapper.readerFor(InputPojo.class)
            .with(INPUT_CSV_SCHEMA)
            .readValues(csvFile);

        //TODO: Catch running exceptions for data mis-mapping
        final List<InputPojo> inputPojoList = iterator.readAll();

        return pojoExtractor.extract(inputPojoList);
    }

    public DefaultCsvFileParser(
        final CsvMapper mapper,
        final PojoExtractor pojoExtractor
    )
    {
        this.mapper = mapper;
        this.pojoExtractor = pojoExtractor;
    }

    private final CsvMapper mapper;
    private final PojoExtractor pojoExtractor;

    public static final CsvSchema INPUT_CSV_SCHEMA = CsvSchema.builder()
        .addColumn(Header.SITE.getName())
        .addColumn(Header.PRODUCT.getName())
        .addColumn(Header.DAY.getName())
        .addColumn(Header.DEMAND.getName())
        .build()
        // TODO: Add options to decide if there is Header for the CSV.
        .withHeader();
}
