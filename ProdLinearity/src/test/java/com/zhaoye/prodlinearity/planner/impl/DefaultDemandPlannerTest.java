package com.zhaoye.prodlinearity.planner.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.zhaoye.prodlinearity.factory.models.ProductionLine;
import com.zhaoye.prodlinearity.factory.models.ProductionLineWithDemands;
import com.zhaoye.prodlinearity.helper.MockHelper;
import com.zhaoye.prodlinearity.planner.DemandPlanner;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public final class DefaultDemandPlannerTest
{
    @Before
    public void initialize() {
        this.demandPlanner = new DefaultDemandPlanner();
    }

    private static Object[] createSingleProductTestPair(
        final String productName,
        final int[] days,
        final int[][] keyDaysWithDemand,
        final int[] demands
    )
    {
        return new Object[] {
            Arrays.asList(
                MockHelper.generateProductionLine(
                    productName,
                    days,
                    keyDaysWithDemand
                )),
            Arrays.asList(
                MockHelper.generateProductionLineWithDemands(
                    productName,
                    days,
                    keyDaysWithDemand,
                    demands
                ))
        };
    }

    /**
     * TestCase.1:
     * No-KeyDaysDemand()
     * +-------------------------+
     * |          Input          |
     * +---------+---+---+---+---+
     * | Day:    | 1 | 2 | 3 | 4 |
     * +---------+---+---+---+---+
     * | Demand: | 0 | 0 | 0 | 0 |
     * +---------+---+---+---+---+
     * |          Output         |
     * +---------+---+---+---+---+
     * | Demand  | 0 | 0 | 0 | 0 |
     * +---------+---+---+---+---+
     */
    private static Object[] createTestCase1()
    {
        int[] days = {1, 2, 3, 4};
        int[] demands = {0, 0, 0, 0};
        int[][] keyDaysWithDemand = {};
        final String productName = "Product1";
        return createSingleProductTestPair(
            productName, days, keyDaysWithDemand, demands
        );
    }

    /**
     * TestCase.2:
     * Input: EmptyInputList
     * Output: EmptyOutputList
     */
    private static Object[] createTestCase2()
    {
        int[] days = {};
        int[] demands = {};
        int[][] keyDaysWithDemand = {};
        final String productName = "Product1";
        return createSingleProductTestPair(
            productName, days, keyDaysWithDemand, demands
        );
    }

    /**
     * TestCase.3:
     * Extend the KeyDay's Demand to other days.
     * +----------------------------------+
     * |               Input              |
     * +---------+---+------+------+------+
     * | Day:    | 1 | 2    | 3    | 4    |
     * +---------+---+------+------+------+
     * | Demand: | 0 | 1000 | 2000 | 0    |
     * +---------+---+------+------+------+
     * |              Output              |
     * +---------+---+------+------+------+
     * | Demand  | 0 | 1000 | 2000 | 2000 |
     * +---------+---+------+------+------+
     */
    private static Object[] createTestCase3()
    {
        int[] days = {1, 2, 3, 4};
        int[] demands = {0, 1000, 2000, 2000};
        int[][] keyDaysWithDemand = {{2, 1000}, {3, 2000}};
        final String productName = "Product1";
        return createSingleProductTestPair(
            productName, days, keyDaysWithDemand, demands
        );
    }


    @Parameters
    public static Collection getTestParameters() {
        return Arrays.asList(
            new Object[][] {
                createTestCase1(),
                createTestCase2(),
                createTestCase3()
            });
    }

    @Test
    public void testPlan()
    {
        assertThat(
            demandPlanner.plan(inputProductionLines),
            is(expectedProductionLinesWithDemands)
        );
    }

    public DefaultDemandPlannerTest(
        final List<ProductionLine> inputProductionLines,
        final List<ProductionLineWithDemands> expectedProductionLinesWithDemands
    )
    {
        this.inputProductionLines = inputProductionLines;
        this.expectedProductionLinesWithDemands = expectedProductionLinesWithDemands;
    }

    private DemandPlanner demandPlanner;
    private List<ProductionLine> inputProductionLines;
    private List<ProductionLineWithDemands> expectedProductionLinesWithDemands;
}
