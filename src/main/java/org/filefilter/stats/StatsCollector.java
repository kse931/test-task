package org.filefilter.stats;

import java.util.*;

public class StatsCollector implements IStatsCollector{

    public Map<String, Map<String, Object>> collectAllStats(Map<String, List<?>> buffer){
        Map<String, Map<String, Object>> allStats = new HashMap<>();

        collectIntStats(allStats, buffer);
        collectDoubleStats(allStats, buffer);
        collectStringStats(allStats, buffer);

        return allStats;
    }

    private void collectIntStats (Map<String, Map<String, Object>> allStats, Map<String, List<?>> buffer) {
        List<Long> ints = (List<Long>) buffer.get("Integers");
        if (!ints.isEmpty()){
            LongSummaryStatistics intStats = ints.stream()
                    .mapToLong(Long::longValue)
                    .summaryStatistics();

            Map<String, Object> intMap = new HashMap<>();
            intMap.put("count", intStats.getCount());
            intMap.put("min", intStats.getMin());
            intMap.put("max", intStats.getMax());
            intMap.put("sum", intStats.getSum());
            intMap.put("average", intStats.getAverage());

            allStats.put("Integers: ", intMap);
        }
    }

    private void collectDoubleStats (Map<String, Map<String, Object>> allStats, Map<String, List<?>> buffer) {
        List<Double> doubles = (List<Double>) buffer.get("Doubles");
        if (!doubles.isEmpty()){
            DoubleSummaryStatistics doubleStats = doubles.stream()
                    .mapToDouble(Double::doubleValue)
                    .summaryStatistics();

            Map<String, Object> doubleMap = new HashMap<>();
            doubleMap.put("count", doubleStats.getCount());
            doubleMap.put("min", doubleStats.getMin());
            doubleMap.put("max", doubleStats.getMax());
            doubleMap.put("sum", doubleStats.getSum());
            doubleMap.put("average", doubleStats.getAverage());

            allStats.put("Doubles: ", doubleMap);
        }
    }

    private void collectStringStats(Map<String, Map<String, Object>> allStats, Map<String, List<?>> buffer){
        List<String> strings = (List<String>) buffer.get("Strings");
        if (!strings.isEmpty()){
            Map<String, Object> stringStats = new HashMap<>();
            stringStats.put("count", strings.size());

            int minLen = strings.stream()
                    .mapToInt(String::length)
                    .min()
                    .orElse(0);

            int maxLen = strings.stream()
                    .mapToInt(String::length)
                    .max()
                    .orElse(0);

            stringStats.put("minlen", minLen);
            stringStats.put("maxlen", maxLen);

            allStats.put("Strings: ", stringStats);
        }
    }
}
