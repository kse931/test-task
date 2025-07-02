package org.filefilter;

import java.util.Map;

public class StatsPrinter {

    private final StatType typeToPrint;

    public StatsPrinter(Config configuration){
        this.typeToPrint = configuration.getStatsType();
    }


    public String getStats(Map<String, Map<String, Object>> statMap){
        return switch (typeToPrint) {
            case SHORT -> getShortStats(statMap);
            case FULL -> getFullStats(statMap);
            case NONE -> "No stats is requested";
        };
    }

    private String getFullStats(Map<String, Map<String, Object>> statMap){
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Map<String, Object>> entry : statMap.entrySet()) {
            stringBuilder.append("\n").append(entry.getKey()).append("\n");
            stringBuilder.append("═".repeat(entry.getKey().length())).append("\n");

            Map<String, Object> values = entry.getValue();
            for (Map.Entry<String, Object> stat : values.entrySet()) {
                Object value = stat.getValue();
                String formattedValue;

                if (value instanceof Double) {
                    formattedValue = String.format("%.4f", value);
                } else if (value instanceof Long || value instanceof Integer) {
                    formattedValue = String.format("%,d", value);
                } else {
                    formattedValue = value.toString();
                }

                stringBuilder.append(String.format("%-10s: %-15s%n", stat.getKey(), formattedValue));
            }
        }

        return stringBuilder.toString();
    }

    private String getShortStats(Map<String, Map<String, Object>> statMap){
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Map<String, Object>> entry : statMap.entrySet()){
            stringBuilder.append("\n").append(entry.getKey()).append("\n");
            stringBuilder.append("═".repeat(entry.getKey().length())).append("\n");

            Map<String, Object> values = entry.getValue();
            Object count = values.get("count");
            String formattedValue;
            if (count instanceof Double) {
                formattedValue = String.format("%.4f", count);
            } else if (count instanceof Long || count instanceof Integer) {
                formattedValue = String.format("%,d", count);
            } else {
                formattedValue = count.toString();
            }
            stringBuilder.append(String.format("%-5s: %-5s%n", "count", formattedValue));
        }

        return stringBuilder.toString();
    }


}
