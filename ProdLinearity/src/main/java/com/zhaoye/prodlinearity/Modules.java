package com.zhaoye.prodlinearity;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.zhaoye.prodlinearity.csv.CsvFileParser;
import com.zhaoye.prodlinearity.csv.CsvFileWriter;
import com.zhaoye.prodlinearity.csv.PojoCreator;
import com.zhaoye.prodlinearity.csv.PojoExtractor;
import com.zhaoye.prodlinearity.csv.impl.DefaultCsvFileParser;
import com.zhaoye.prodlinearity.csv.impl.DefaultCsvFileWriter;
import com.zhaoye.prodlinearity.csv.impl.DefaultPojoCreator;
import com.zhaoye.prodlinearity.csv.impl.DefaultPojoExtractor;
import com.zhaoye.prodlinearity.factory.ProductionLinesFactory;
import com.zhaoye.prodlinearity.factory.ProgressBarFactory;
import com.zhaoye.prodlinearity.factory.SitesFactory;
import com.zhaoye.prodlinearity.factory.impl.DefaultProductionLinesFactory;
import com.zhaoye.prodlinearity.factory.impl.DefaultProgressBarFactory;
import com.zhaoye.prodlinearity.factory.impl.DefaultSitesFactory;
import com.zhaoye.prodlinearity.planner.DemandPlanner;
import com.zhaoye.prodlinearity.planner.ProducePlanner;
import com.zhaoye.prodlinearity.planner.impl.DefaultDemandPlanner;
import com.zhaoye.prodlinearity.planner.impl.DefaultProducePlanner;

public class Modules
{
    public static final ProgressBarFactory PROGRESS_BAR_FACTORY
        = new DefaultProgressBarFactory();

    public static final CsvMapper CSV_MAPPER = new CsvMapper();

    public static final PojoExtractor POJO_EXTRACTOR =
        new DefaultPojoExtractor(PROGRESS_BAR_FACTORY);

    public static final CsvFileParser CSV_FILE_PARSER =
        new DefaultCsvFileParser(CSV_MAPPER, POJO_EXTRACTOR);

    public static final ProductionLinesFactory PRODUCTION_LINES_FACTORY =
        new DefaultProductionLinesFactory();

    public static final DemandPlanner DEMAND_PLANNER = new DefaultDemandPlanner();

    public static final ProducePlanner PRODUCE_PLANNER = new DefaultProducePlanner();

    public static final SitesFactory SITES_FACTORY = new DefaultSitesFactory(
        PRODUCTION_LINES_FACTORY,
        DEMAND_PLANNER,
        PRODUCE_PLANNER,
        PROGRESS_BAR_FACTORY
    );

    public static final PojoCreator POJO_CREATOR = new DefaultPojoCreator(PROGRESS_BAR_FACTORY);
    public static final CsvFileWriter CSV_FILE_WRITER =
        new DefaultCsvFileWriter(CSV_MAPPER, POJO_CREATOR);
}
