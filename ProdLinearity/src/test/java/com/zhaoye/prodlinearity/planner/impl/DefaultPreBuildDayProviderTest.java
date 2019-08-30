package com.zhaoye.prodlinearity.planner.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.zhaoye.prodlinearity.helper.MockHelper;
import com.zhaoye.prodlinearity.planner.PreBuildDayProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class DefaultPreBuildDayProviderTest
{
    @Test
    public void testProvide()
    {
        final int preBuildDay = MockHelper.RANDOM.nextInt(10);
        preBuildDayProvider = new DefaultPreBuildDayProvider(preBuildDay);

        assertThat(preBuildDayProvider.provide(), is(preBuildDay));
    }

    private PreBuildDayProvider preBuildDayProvider;
}
