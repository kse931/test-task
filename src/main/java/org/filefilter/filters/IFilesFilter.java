package org.filefilter.filters;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IFilesFilter {
    Map<String, List<?>> processFiles() throws IOException;
}
