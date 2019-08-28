package com.zhaoye.prodlinearity;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "ProductionLinearity", description = "Production Linearity tool for helping to plan the production.")
public final class Application implements Callable<Integer>
{
    public static void main(final String[] args)
    {
        final int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }

    @CommandLine.Option(names = {"-p", "--PreBuildDays"}, description = "The number of pre-build days for production.", required = true)
    private int preBuildDays;

    @CommandLine.Option(names = {"-i", "--InputFilePath"}, description = "The input csv file.", required = true)
    private String inputFilePath;

    @CommandLine.Option(names = {"-o", "--OutputFilePath"}, description = "The input csv file.", required = true)
    private String outputFilePath;

    @Override
    public Integer call() {
        return CommandLine.ExitCode.OK;
    }
}