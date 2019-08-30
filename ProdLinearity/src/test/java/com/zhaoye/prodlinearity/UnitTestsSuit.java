package com.zhaoye.prodlinearity;

import com.zhaoye.prodlinearity.csv.impl.DefaultPojoExtractorTest;
import com.zhaoye.prodlinearity.planner.impl.DefaultDemandPlannerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    DefaultPojoExtractorTest.class,
    DefaultDemandPlannerTest.class
})
public class UnitTestsSuit { }