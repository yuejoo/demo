package com.zhaoye.prodlinearity.factory;

import me.tongfei.progressbar.ProgressBar;

public interface ProgressBarFactory
{
    public ProgressBar get(final String taskName, final long count);
}
