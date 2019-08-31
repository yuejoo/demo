package com.zhaoye.prodlinearity.csv.impl;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.zhaoye.prodlinearity.csv.CsvFileWriter;
import com.zhaoye.prodlinearity.csv.PojoCreator;
import com.zhaoye.prodlinearity.csv.models.Header;
import com.zhaoye.prodlinearity.csv.models.OutputPojo;
import com.zhaoye.prodlinearity.factory.models.Site;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Write the planned sites into the csv file.
 */
public final class DefaultCsvFileWriter implements CsvFileWriter
{
    @Override
    public void write(final File file, final Collection<Site> sites) throws IOException
    {
        final ObjectWriter objectWriter = mapper
            .writerFor(OutputPojo.class).with(OUTPUT_CSV_SCHEMA);
        final List<OutputPojo> outputPojoList = pojoCreator.create(sites);

        System.out.println("Saving Csv File...");
        objectWriter.writeValues(file).writeAll(outputPojoList);
    }

    public DefaultCsvFileWriter(
        final CsvMapper mapper,
        final PojoCreator pojoCreator
    )
    {
        this.mapper = mapper;
        this.pojoCreator = pojoCreator;
    }

    private final CsvMapper mapper;
    private final PojoCreator pojoCreator;

    public static final CsvSchema OUTPUT_CSV_SCHEMA = CsvSchema.builder()
        .addColumn(Header.SITE.getName())
        .addColumn(Header.PRODUCT.getName())
        .addColumn(Header.DAY.getName())
        .addColumn(Header.DEMAND.getName())
        .addColumn(Header.PRODUCE.getName())
        .build()
        // TODO: Add options to decide if there is Header for the CSV.
        .withHeader();
}
