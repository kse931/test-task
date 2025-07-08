package org.filefilter.printers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IFilesPrinter {
    void printToFiles(Map<String, List<?>> buffer) throws IOException;
}
