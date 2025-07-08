package org.filefilter.stats;

import java.util.List;
import java.util.Map;

public interface IStatsCollector {
    Map<String, Map<String, Object>> collectAllStats(Map<String, List<?>> buffer);
}
