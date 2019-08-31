package com.zhaoye.prodlinearity.factory.impl;

import com.zhaoye.prodlinearity.factory.ProgressBarFactory;
import me.tongfei.progressbar.ProgressBar;

public final class DefaultProgressBarFactory implements ProgressBarFactory
{
    @Override
    public ProgressBar get(final String taskName, final long count)
    {
        return new ProgressBar(
            taskName,
            count,
            50
        );
    }
}
