package org.filefilter.stats;

import java.util.Map;

public interface IStatsPrinter {
    public String getStats(Map<String, Map<String, Object>> statMap);
}
