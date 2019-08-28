package com.zhaoye.prodlinearity;

import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * Hello world!
 *
 */
@CommandLine.Command(name = "productionlinearity", description = "Production Linearity Tool.")
public class Application implements Callable<Void>
{
    public static void main(String[] args)
    {
        CommandLine.call(new Application(), args);
    }

    @CommandLine.Option(names = {"-p", "--PreBuildDays"})
    private int preBuildDays;

    @CommandLine.Option(names = {"-i", "--InputFilePath"})
    private String inputFilePath;

    @CommandLine.Option(names = {"-o", "--OutputFilePath"})
    private String outputFilePath;

    @Override
    public Void call() {
        System.out.println("Hello World!");
        return null;
    }
}
