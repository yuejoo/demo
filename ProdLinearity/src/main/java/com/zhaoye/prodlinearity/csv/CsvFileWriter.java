package com.zhaoye.prodlinearity.csv;

import com.zhaoye.prodlinearity.factory.models.Site;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface CsvFileWriter
{
    void write(final File file, final Collection<Site> sites) throws IOException;
}
