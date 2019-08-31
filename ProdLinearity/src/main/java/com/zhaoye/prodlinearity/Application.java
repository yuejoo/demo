package com.zhaoye.prodlinearity;

import com.zhaoye.prodlinearity.planner.impl.DefaultPreBuildDayProvider;
import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "ProductionLinearity", description = "Production Linearity tool for helping to plan the production.")
public final class Application implements Callable<Integer>
{
    public static void main(final String[] args)
    {
        final int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        try {
            File inputFile = new File(inputFilePath);
            File outputFile = new File(outputFilePath);

            Modules.CSV_FILE_WRITER.write(
                outputFile,
                Modules.SITES_FACTORY.create(
                    Modules.CSV_FILE_PARSER.parse(inputFile),
                    new DefaultPreBuildDayProvider(preBuildDays)
                )
            );
        }
        catch (Exception e)
        {
            System.out.println(e);

        }
        return CommandLine.ExitCode.OK;
    }


    @CommandLine.Option(names = {"-p", "--PreBuildDays"}, description = "The number of pre-build days for production.", required = true)
    private int preBuildDays;

    @CommandLine.Option(names = {"-i", "--InputFilePath"}, description = "The input csv file.", required = true)
    private String inputFilePath;

    @CommandLine.Option(names = {"-o", "--OutputFilePath"}, description = "The input csv file.", required = true)
    private String outputFilePath;
}