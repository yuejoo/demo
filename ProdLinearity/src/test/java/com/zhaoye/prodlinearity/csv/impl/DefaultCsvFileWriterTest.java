package com.zhaoye.prodlinearity.csv.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.zhaoye.prodlinearity.csv.CsvFileParser;
import com.zhaoye.prodlinearity.csv.CsvFileWriter;
import com.zhaoye.prodlinearity.csv.PojoCreator;
import com.zhaoye.prodlinearity.csv.PojoExtractor;
import com.zhaoye.prodlinearity.csv.models.CsvContainer;
import com.zhaoye.prodlinearity.csv.models.InputPojo;
import com.zhaoye.prodlinearity.csv.models.OutputPojo;
import com.zhaoye.prodlinearity.factory.models.Site;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public final class DefaultCsvFileWriterTest
{
    @Before
    public void initialize() throws IOException
    {
        Mockito.when(
            csvMapper.writerFor(OutputPojo.class)
                .with(DefaultCsvFileWriter.OUTPUT_CSV_SCHEMA)
        ).thenReturn(objectWriter);

        Mockito.when(pojoCreator.create(inputSiteList)).thenReturn(pojoList);
    }

    @Test
    public void testWrite() throws IOException
    {
        new DefaultCsvFileWriter(
            csvMapper,
            pojoCreator
        ).write(csvFile, inputSiteList);

        Mockito.verify(objectWriter.writeValues(csvFile)).writeAll(pojoList);
    }

    @Test(expected = IOException.class)
    public void testIOException() throws IOException
    {
        Mockito.when(objectWriter.writeValues(csvFile).writeAll(pojoList)).thenThrow(new IOException("File Not Found"));
        new DefaultCsvFileWriter(
            csvMapper,
            pojoCreator
        ).write(csvFile, inputSiteList);
    }

    //TODO: Test Write RunTime Exceptions.

    @Mock private File csvFile;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) private CsvMapper csvMapper;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) private ObjectWriter objectWriter;
    @Mock private PojoCreator pojoCreator;
    @Mock private Collection<Site> inputSiteList;
    @Mock private List<OutputPojo> pojoList;
}
