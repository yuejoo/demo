package com.zhaoye.prodlinearity.csv.impl;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.zhaoye.prodlinearity.csv.CsvFileParser;
import com.zhaoye.prodlinearity.csv.PojoExtractor;
import com.zhaoye.prodlinearity.csv.models.InputPojo;
import com.zhaoye.prodlinearity.csv.models.CsvContainer;
import com.zhaoye.prodlinearity.csv.models.Header;

import com.zhaoye.prodlinearity.exceptions.InvalidInputFileException;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Extract the CsvContainer from the CsvFile.
 */
public final class DefaultCsvFileParser implements CsvFileParser
{
    @Override
    public CsvContainer parse(
        final File csvFile,
        final CsvSchema csvSchema
    ) throws IOException, InvalidInputFileException
    {
        System.out.println("Loading Csv File...");
        final MappingIterator<InputPojo> iterator = mapper.readerFor(InputPojo.class)
            .with(csvSchema)
            .readValues(csvFile);

        List<InputPojo> inputPojoList = null;
        try
        {
            inputPojoList = iterator.readAll();
        }
        catch (IOException e)
        {
            throw new InvalidInputFileException(e.getMessage());
        }

        if(inputPojoList == null || inputPojoList.isEmpty())
        {
            throw new InvalidInputFileException("The input file is Empty. Please use a valid input file.");
        }

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
        .build();
}
