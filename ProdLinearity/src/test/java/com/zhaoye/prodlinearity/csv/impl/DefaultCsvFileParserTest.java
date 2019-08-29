package com.zhaoye.prodlinearity.csv.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.zhaoye.prodlinearity.csv.CsvFileParser;
import com.zhaoye.prodlinearity.csv.PojoExtractor;
import com.zhaoye.prodlinearity.csv.models.CsvContainer;
import com.zhaoye.prodlinearity.csv.models.Pojo;
import java.io.File;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public final class DefaultCsvFileParserTest
{
    @Before
    public void initialize() throws IOException
    {
        Mockito.when(iterator.readAll()).thenReturn(pojoList);
        Mockito.when(pojoExtractor.extract(pojoList)).thenReturn(csvContainer);
        Mockito.when(csvMapper.readerFor(Pojo.class)
            .with(DefaultCsvFileParser.INPUT_CSV_SCHEMA)
            .readValues(csvFile)
        ).thenReturn(iterator);
    }

    @Test
    public void testParse() throws IOException
    {
        final CsvFileParser csvFileParser = new DefaultCsvFileParser(
            csvMapper,
            pojoExtractor
        );
        assertThat(csvFileParser.parse(csvFile), is(csvContainer));
    }

    @Test(expected = IOException.class)
    public void testIOException() throws IOException
    {
        Mockito.when(iterator.readAll()).thenThrow(new IOException("File Not Found"));
        final CsvFileParser csvFileParser = new DefaultCsvFileParser(
            csvMapper,
            pojoExtractor
        );
        csvFileParser.parse(csvFile);
    }

    //TODO: Test Parsing RunTime Exceptions.

    @Mock private File csvFile;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) private CsvMapper csvMapper;
    @Mock private PojoExtractor pojoExtractor;
    @Mock private MappingIterator iterator;
    @Mock private List<Pojo> pojoList;
    @Mock private CsvContainer csvContainer;
}
