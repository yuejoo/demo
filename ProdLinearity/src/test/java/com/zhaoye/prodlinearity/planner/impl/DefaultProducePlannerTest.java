package com.zhaoye.prodlinearity.planner.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.zhaoye.prodlinearity.factory.models.ProductionLineWithDemands;
import com.zhaoye.prodlinearity.factory.models.ProductionLineWithProduces;
import com.zhaoye.prodlinearity.helper.MockHelper;
import com.zhaoye.prodlinearity.planner.ProducePlanner;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public final class DefaultProducePlannerTest
{
    @Before
    public void initialize() {
        this.producePlanner = new DefaultProducePlanner();
    }

    private static Object[] createSingleProductTestPair(
        final String productName,
        final int[] days,
        final int[][] keyDaysWithDemand,
        final int[] demands,
        final int[] produces,
        final int preBuildDays
    )
    {
        return new Object[] {
            preBuildDays,
            Arrays.asList(
                MockHelper.generateProductionLineWithDemands(
                    productName,
                    days,
                    keyDaysWithDemand,
                    demands
                )),
            Arrays.asList(
                MockHelper.generateProductionLineWithProduce(
                    productName,
                    days,
                    keyDaysWithDemand,
                    demands,
                    produces
                )).stream().collect(Collectors.toCollection(()->new TreeSet<>(
                    Comparator.comparing(pl -> pl.product().value())
            )))
        };
    }

    /**
     * TestCase.0:
     * Non-Consecutive Days in the input
     * +-------------------------+
     * |          Input          |
     * +---------+---+---+---+---+
     * | Day:    | 1 | 5 | 6 | 7 |
     * +---------+---+---+---+---+
     * | Demand: | 0 | 0 | 3 | 3 |
     * +---------+---+---+---+---+
     * |          Output         |
     * +---------+---+---+---+---+
     * | Produce | 1 | 2 | 3 | 3 |
     * +---------+---+---+---+---+
     */
    private static Object[] createTestCase0()
    {
        int[] days = {1, 5, 6, 7};
        int[] demands = {0, 0, 3, 3};
        int[] produces = {1, 2, 3, 3};

        int[][] keyDaysWithDemand = {{6, 3}};
        final String productName = "Product1";
        return createSingleProductTestPair(
            productName, days, keyDaysWithDemand, demands, produces, 3
        );
    }

    /**
     * TestCase.1:
     * No-Demands at all. (no key days)
     * +-------------------------+
     * |          Input          |
     * +---------+---+---+---+---+
     * | Day:    | 1 | 2 | 3 | 4 |
     * +---------+---+---+---+---+
     * | Demand: | 0 | 0 | 0 | 0 |
     * +---------+---+---+---+---+
     * |          Output         |
     * +---------+---+---+---+---+
     * | Produce | 0 | 0 | 0 | 0 |
     * +---------+---+---+---+---+
     */
    private static Object[] createTestCase1()
    {
        int[] days = {1, 2, 3, 4};
        int[] demands = {0, 0, 0, 0};
        int[] produces = {0, 0, 0, 0};

        int[][] keyDaysWithDemand = {};
        final String productName = "Product1";
        return createSingleProductTestPair(
            productName, days, keyDaysWithDemand, demands, produces, 3
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
        int[] produces = {};
        int[][] keyDaysWithDemand = {};
        final String productName = "Product1";
        return createSingleProductTestPair(
            productName, days, keyDaysWithDemand, demands, produces, 3
        );
    }

    /**
     * TestCase.3:
     * Test Pre-BuildDays Larger than the KeyDay's Gap.
     * +------------------------------------+
     * |        Input (Pre-BuildDay 3)      |
     * +---------+-----+------+------+------+
     * | Day:    | 1   | 2    | 3    | 4    |
     * +---------+-----+------+------+------+
     * | Demand: | 0   | 1000 | 1000 | 2000 |
     * +---------+-----+------+------+------+
     * |               Output               |
     * +---------+-----+------+------+------+
     * | Produce | 500 | 1000 | 1500 | 2000 |
     * +---------+-----+------+------+------+
     */
    private static Object[] createTestCase3()
    {
        int[] days = {1, 2, 3, 4};
        int[] demands = {0, 1000, 1000, 2000};
        int[] produces = {500, 1000, 1500, 2000};

        int[][] keyDaysWithDemand = {{2, 1000}, {4, 2000}};
        final String productName = "Product1";
        return createSingleProductTestPair(
            productName, days, keyDaysWithDemand, demands, produces, 3
        );
    }

    /**
     * TestCase.4:
     * Test Pre-BuildDays Smaller than the KeyDay's Gap.
     * +----------------------------------------------------------------+
     * |                       Input (Pre-Build 2)                      |
     * +---------+-----+------+------+------+------+------+------+------+
     * | Day:    | 1   | 2    | 3    | 4    | 5    | 6    | 7    | 8    |
     * +---------+-----+------+------+------+------+------+------+------+
     * | Demand: | 0   | 1000 | 1000 | 1000 | 1000 | 4000 | 4000 | 5000 |
     * +---------+-----+------+------+------+------+------+------+------+
     * |                             Output                             |
     * +---------+-----+------+------+------+------+------+------+------+
     * | Produce | 500 | 1000 | 1000 | 2000 | 3000 | 4000 | 4500 | 5000 |
     * +---------+-----+------+------+------+------+------+------+------+
     */
    private static Object[] createTestCase4()
    {
        int[] days = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] demands = {0, 1000, 1000, 1000, 1000, 4000, 4000, 5000};
        int[] produces = {500, 1000, 1000, 2000, 3000, 4000, 4500, 5000};

        int[][] keyDaysWithDemand = {{2, 1000}, {6, 4000}, {8, 5000}};
        final String productName = "Product1";
        return createSingleProductTestPair(
            productName, days, keyDaysWithDemand, demands, produces, 2
        );
    }

    @Parameters
    public static Collection getTestParameters() {
        return Arrays.asList(
            new Object[][] {
                createTestCase0(),
                createTestCase1(),
                createTestCase2(),
                createTestCase3(),
                createTestCase4()
            });
    }

    @Test
    public void testPlan()
    {
        assertThat(
            producePlanner.plan(inputProductionLines, inputPreBuildDays).toArray(),
            is(expectedProductionLinesWithProduces.toArray())
        );
    }

    public DefaultProducePlannerTest(
        final int inputPreBuildDays,
        final List<ProductionLineWithDemands> inputProductionLines,
        final Collection<ProductionLineWithProduces> expectedProductionLinesWithProduces
    )
    {
        this.inputPreBuildDays = inputPreBuildDays;
        this.inputProductionLines = inputProductionLines;
        this.expectedProductionLinesWithProduces = expectedProductionLinesWithProduces;
    }

    private ProducePlanner producePlanner;
    private int inputPreBuildDays;
    private List<ProductionLineWithDemands> inputProductionLines;
    private Collection<ProductionLineWithProduces> expectedProductionLinesWithProduces;
}
