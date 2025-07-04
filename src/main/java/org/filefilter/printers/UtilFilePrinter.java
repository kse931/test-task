package org.filefilter.printers;

import org.filefilter.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

public class UtilFilePrinter implements IFilesPrinter{

    private final Config configuration;

    public UtilFilePrinter(Config config){
        this.configuration = config;
    }

    public void printToFiles(Map<String, List<?>> buffer) throws IOException {
        Path directoryPath = Paths.get(configuration.getOutputDir());
        if (!Files.exists(directoryPath)){
            Files.createDirectories(directoryPath);
        }

        printData(directoryPath, "strings.txt", (List<String>) buffer.get("Strings"));
        printData(directoryPath, "integers.txt", buffer.get("Integers")
                .stream()
                .map(Object::toString)
                .toList());
        printData(directoryPath, "doubles.txt", buffer.get("Doubles")
                .stream()
                .map(Object::toString)
                .toList());

    }


    private void printData(Path dirPath, String filename, List<String> toWrite) throws IOException{
        if (toWrite.isEmpty()) return;

        String fileName = configuration.getFilePrefix() + filename;
        Path pathToFile = dirPath.resolve(fileName);

        StandardOpenOption[] openOptions = configuration.isEnableAppend() ?
                new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.APPEND} :
                new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};

        Files.write(pathToFile, toWrite, openOptions);
    }

}
