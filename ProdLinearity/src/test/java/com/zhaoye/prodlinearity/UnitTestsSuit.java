package com.zhaoye.prodlinearity;

import com.zhaoye.prodlinearity.csv.impl.DefaultPojoExtractorTest;
import com.zhaoye.prodlinearity.factory.imipl.DefaultProductionLinesFactoryTest;
import com.zhaoye.prodlinearity.planner.impl.DefaultDemandPlannerTest;
import com.zhaoye.prodlinearity.planner.impl.DefaultProducePlannerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite Runner for running Parameterized unit tests
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    DefaultPojoExtractorTest.class,
    DefaultDemandPlannerTest.class,
    DefaultProducePlannerTest.class,
    DefaultProductionLinesFactoryTest.class
})
public class UnitTestsSuit { }