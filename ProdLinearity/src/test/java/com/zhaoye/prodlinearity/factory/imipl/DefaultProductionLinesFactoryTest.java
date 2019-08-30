package com.zhaoye.prodlinearity.factory.imipl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.sun.tools.javac.util.Pair;
import com.zhaoye.prodlinearity.factory.ProductionLinesFactory;
import com.zhaoye.prodlinearity.factory.impl.DefaultProductionLinesFactory;
import com.zhaoye.prodlinearity.factory.models.ProductionLine;
import com.zhaoye.prodlinearity.helper.MockHelper;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class DefaultProductionLinesFactoryTest
{
    @Before
    public void initialize() {
        this.productionLinesFactory = new DefaultProductionLinesFactory();
    }

    private static Object[] createSingleProductTestPair(
        final String productName,
        final int[] days,
        final int[][] keyDaysWithDemand
    )
    {
        return new Object[] {
            MockHelper.createSingleProductToKeyDayDemandsPairMap(
                productName,
                days,
                keyDaysWithDemand
            ),
            Arrays.asList(
                MockHelper.generateProductionLine(
                    productName,
                    days,
                    keyDaysWithDemand
            ))
        };
    }

    /**
     * TestCase1:
     * Input: EmptyInputMap
     * Output: EmptyList
     */
     private static Object[] createTestCase1()
     {
         return createSingleProductTestPair(
             "Product1",
             new int[]{},
             new int[][]{}
         );
     }

    /**
     * TestCase2:
     * Input: Empty DayToDemandPairMap
     * Output: Empty DayToDemandList
     */
    private static Object[] createTestCase2()
    {
        return createSingleProductTestPair(
            "Product1",
            new int[]{1, 2, 3, 4}, // days
            new int[][]{} //keyDayToDemands
        );
    }

    /**
     * TestCase3:
     * Input:
     * KeyDayWithDemands:  2(200), 4(400)
     * Days: 1 2 3 4 5
     * Output:
     * Days: 1 2 3 4 5
     * KeyDayWithDemands: 2(200), 4(400)
     */
    private static Object[] createTestCase3()
    {
        return createSingleProductTestPair(
            "Product1",
            new int[]{1, 2, 3, 4}, // days
            new int[][]{{2, 200}, {4, 400}} //keyDayToDemands
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
    public void testCreate()
    {
        assertThat(
            productionLinesFactory.create(inputProductToDayDemandPairMap),
            is(expectedProductionLines)
        );
    }

    public DefaultProductionLinesFactoryTest(
        final Map<String, TreeSet<Pair<Integer, Integer>>> inputProductToDayDemandPairMap,
        final List<ProductionLine> expectedProductionLines
    )
    {
        this.inputProductToDayDemandPairMap = inputProductToDayDemandPairMap;
        this.expectedProductionLines = expectedProductionLines;
    }

    private ProductionLinesFactory productionLinesFactory;
    private Map<String, TreeSet<Pair<Integer, Integer>>> inputProductToDayDemandPairMap;
    private List<ProductionLine> expectedProductionLines;
}
