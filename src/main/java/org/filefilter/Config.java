package org.filefilter;

import org.filefilter.stats.StatType;

import java.util.List;

public class Config {
    private final String outputDir;
    private final String filePrefix;
    private final StatType statsType;
    private final boolean enableAppend;
    private final List<String> inputFiles;

    public Config(String outputDir, String filePrefix, boolean enableAppend, boolean fullStats, boolean shortStats, List<String> inputFiles){
        this.outputDir = outputDir;
        this.filePrefix = filePrefix;
        this.enableAppend = enableAppend;
        this.inputFiles = inputFiles;
        if ((!shortStats) && (!fullStats)) this.statsType = StatType.NONE;
        else if (shortStats && fullStats) this.statsType = StatType.FULL;
        else this.statsType = fullStats ? StatType.FULL : StatType.SHORT;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public String getFilePrefix() {
        return filePrefix;
    }

    public StatType getStatsType() {
        return statsType;
    }

    public boolean isEnableAppend() {
        return enableAppend;
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }
}
