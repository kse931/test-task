package org.filefilter;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;


@Command(name = "util", mixinStandardHelpOptions = true,
        description = "Distributes different types of data from the input file across several")
public class Main implements Callable<Integer> {

    @Option(names = {"-o"}, description = "Output directory (current directory is used by default)")
    private File outputDirectory = new File(".");

    @Option(names = {"-p"}, description = "Prefix for output file")
    private String prefix = "";

    @Option(names = {"-a"}, description = "Add to existing files")
    private boolean append = false;

    @Option(names = {"-f"}, description = "Show full stats")
    private boolean fullStats = false;

    @Option(names = {"-s"}, description = "Show short stats")
    private boolean shortStats = false;

    @Parameters(arity = "1..*", description = "Input files")
    private List<File> inputFiles;

    @Override
    public Integer call() throws Exception {
        System.out.println(outputDirectory + " " + prefix + " " + append + " " + fullStats + " " + shortStats + " " + inputFiles);
        return 0;
    }

    public static void main(String[] args) {
        int exit = new CommandLine(new Main()).execute(args);
        System.exit(exit);
    }
}
