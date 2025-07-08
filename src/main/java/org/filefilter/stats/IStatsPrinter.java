package org.filefilter.stats;

import java.util.Map;

public interface IStatsPrinter {
    String getStats(Map<String, Map<String, Object>> statMap);
}
