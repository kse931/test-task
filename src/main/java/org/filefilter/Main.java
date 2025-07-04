package org.filefilter;

import org.filefilter.filters.UtilFileFilter;
import org.filefilter.printers.UtilFilePrinter;
import org.filefilter.stats.StatsCollector;
import org.filefilter.stats.StatsPrinter;
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
            Config utilConfig = new Config(outputDirectory, prefix, append, fullStats, shortStats, inputFiles);
            ApplicationProcess applicationProcess = new ApplicationProcess(new UtilFileFilter(utilConfig), new StatsCollector(),
                    new UtilFilePrinter(utilConfig), new StatsPrinter(utilConfig));
            applicationProcess.begin();
            return 0;

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return 1;
        }
    }
}
