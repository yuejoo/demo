package com.zhaoye.prodlinearity.csv.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.zhaoye.prodlinearity.csv.CsvFileParser;
import com.zhaoye.prodlinearity.csv.PojoExtractor;
import com.zhaoye.prodlinearity.csv.models.CsvContainer;
import com.zhaoye.prodlinearity.csv.models.InputPojo;
import com.zhaoye.prodlinearity.exceptions.InvalidInputFileException;
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
    public void initialize() throws Exception
    {
        Mockito.when(iterator.readAll()).thenReturn(inputPojoList);
        Mockito.when(pojoExtractor.extract(inputPojoList)).thenReturn(csvContainer);
        Mockito.when(csvMapper.readerFor(InputPojo.class)
            .with(DefaultCsvFileParser.INPUT_CSV_SCHEMA)
            .readValues(csvFile)
        ).thenReturn(iterator);
        Mockito.when(inputPojoList.isEmpty()).thenReturn(false);
    }

    @Test
    public void testParse() throws Exception
    {
        final CsvFileParser csvFileParser = new DefaultCsvFileParser(
            csvMapper,
            pojoExtractor
        );
        assertThat(csvFileParser.parse(csvFile, DefaultCsvFileParser.INPUT_CSV_SCHEMA), is(csvContainer));
    }

    @Test(expected = IOException.class)
    public void testIOException() throws Exception
    {
        Mockito.when(csvMapper.readerFor(InputPojo.class)
            .with(DefaultCsvFileParser.INPUT_CSV_SCHEMA)
            .readValues(csvFile)
        ).thenThrow(new IOException("File Not Found"));
        final CsvFileParser csvFileParser = new DefaultCsvFileParser(
            csvMapper,
            pojoExtractor
        );
        csvFileParser.parse(csvFile, DefaultCsvFileParser.INPUT_CSV_SCHEMA);
    }

    @Test(expected = InvalidInputFileException.class)
    public void testInvalidInputFileException() throws Exception
    {
        Mockito.when(iterator.readAll()).thenThrow(new IOException("InvalidFormatException"));
        final CsvFileParser csvFileParser = new DefaultCsvFileParser(
            csvMapper,
            pojoExtractor
        );
        csvFileParser.parse(csvFile, DefaultCsvFileParser.INPUT_CSV_SCHEMA);
    }

    @Test(expected = InvalidInputFileException.class)
    public void testEmptyFile() throws Exception
    {
        Mockito.when(inputPojoList.isEmpty()).thenReturn(true);
        final CsvFileParser csvFileParser = new DefaultCsvFileParser(
            csvMapper,
            pojoExtractor
        );
        csvFileParser.parse(csvFile, DefaultCsvFileParser.INPUT_CSV_SCHEMA);
    }

    @Mock private File csvFile;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) private CsvMapper csvMapper;
    @Mock private PojoExtractor pojoExtractor;
    @Mock private MappingIterator iterator;
    @Mock private List<InputPojo> inputPojoList;
    @Mock private CsvContainer csvContainer;
}
