package org.filefilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class UtilFilePrinter {

    private UtilFileFilter utilFileFilter;

    public UtilFilePrinter(UtilFileFilter utilFileFilter){
        this.utilFileFilter = utilFileFilter;
    }

    public void printToFiles() throws IOException {
        Path directoryPath = Paths.get(utilFileFilter.getConfiguration().getOutputDir());
        if (!Files.exists(directoryPath)){
            Files.createDirectories(directoryPath);
        }

        printData(directoryPath, "strings.txt", utilFileFilter.getStringsPrintQueue());
        printData(directoryPath, "integers.txt", utilFileFilter.getIntegerPrintQueue()
                .stream()
                .map(Object::toString)
                .toList());
        printData(directoryPath, "doubles.txt", utilFileFilter.getDoublesPrintQueue()
                .stream()
                .map(Object::toString)
                .toList());

    }


    private void printData(Path dirPath, String filename, List<String> toWrite) throws IOException{
        if (toWrite.isEmpty()) return;

        String fileName = utilFileFilter.getConfiguration().getFilePrefix() + filename;
        Path pathToFile = dirPath.resolve(fileName);

        StandardOpenOption[] openOptions = utilFileFilter.getConfiguration().isEnableAppend() ?
                new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.APPEND} :
                new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};

        Files.write(pathToFile, toWrite, openOptions);
    }

}
