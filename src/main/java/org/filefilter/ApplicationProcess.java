package org.filefilter;

import org.filefilter.filters.IFilesFilter;
import org.filefilter.printers.IFilesPrinter;
import org.filefilter.stats.IStatsCollector;
import org.filefilter.stats.IStatsPrinter;

import java.util.List;
import java.util.Map;

public class ApplicationProcess {

    private final IFilesFilter fileFilter;
    private final IStatsCollector statsCollector;
    private final IFilesPrinter filesPrinter;
    private final IStatsPrinter statsPrinter;

    public ApplicationProcess(IFilesFilter filesFilter, IStatsCollector statsCollector, IFilesPrinter filesPrinter, IStatsPrinter statsPrinter){
        this.fileFilter = filesFilter;
        this.statsCollector = statsCollector;
        this.filesPrinter = filesPrinter;
        this.statsPrinter = statsPrinter;
    }

    public void begin() throws Exception {
        System.out.println("Processing...");
        Map<String, List<?>> buffer = fileFilter.processFiles();
        System.out.println("Finished");
        filesPrinter.printToFiles(buffer);
        System.out.println("Requested stats: ");
        System.out.println(statsPrinter.getStats(statsCollector.collectAllStats(buffer)));
    }
}
