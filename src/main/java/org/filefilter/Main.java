package org.filefilter;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.util.List;
import java.util.concurrent.Callable;


@Command(name = "util", mixinStandardHelpOptions = true,
        description = "Distributes different types of data from the input file across several")
public class Main implements Callable<Integer> {

    @Option(names = {"-o"}, description = "Output directory (current directory is used by default)")
    private String outputDirectory = ".";

    @Option(names = {"-p"}, description = "Prefix for output file")
    private String prefix = "";

    @Option(names = {"-a"}, description = "Add to existing files")
    private boolean append = false;

    @Option(names = {"-f"}, description = "Show full stats")
    private boolean fullStats = false;

    @Option(names = {"-s"}, description = "Show short stats")
    private boolean shortStats = false;

    @Parameters(arity = "1..*", description = "Input files")
    private List<String> inputFiles;

    public static void main(String[] args) {
        int exit = new CommandLine(new Main()).execute(args);
        System.exit(exit);
    }

    @Override
    public Integer call() {
        try {
            System.out.println("Processing...");

            Config utilConfig = new Config(outputDirectory, prefix, append, fullStats, shortStats, inputFiles);
            FileFilter fileFilter = new FileFilter(utilConfig);
            fileFilter.processFiles();
            StatsCollector statsCollector = new StatsCollector(fileFilter);

            System.out.println(statsCollector.collectAllStats());
            return 0;
        } catch (Exception e) {
            System.out.println("Error occured: " + e.getMessage());
            return 1;
        }

    }
}
