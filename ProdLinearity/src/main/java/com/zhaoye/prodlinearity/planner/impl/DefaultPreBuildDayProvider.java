package com.zhaoye.prodlinearity.planner.impl;

import com.zhaoye.prodlinearity.planner.PreBuildDayProvider;

public final class DefaultPreBuildDayProvider implements PreBuildDayProvider
{
    @Override
    public int provide()
    {
        return preBuildDay;
    }

    public DefaultPreBuildDayProvider(final int preBuildDay)
    {
        this.preBuildDay = preBuildDay;
    }

    private final int preBuildDay;
}
