package org.filefilter.filters;

import org.filefilter.Config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilFileFilter implements IFilesFilter{

    private final Config configuration;

    private final List<String> stringsPrintQueue = new ArrayList<>();
    private final List<Long> integerPrintQueue = new ArrayList<>();
    private final List<Double> doublesPrintQueue = new ArrayList<>();

    public UtilFileFilter(Config configuration){
        this.configuration = configuration;
    }

    public Map<String, List<?>> processFiles() throws IOException{
        for (String name : configuration.getInputFiles()){
            processFile(name);
        }
        Map<String, List<?>> output = new HashMap<>();
        output.put("Strings", stringsPrintQueue);
        output.put("Integers", integerPrintQueue);
        output.put("Doubles", doublesPrintQueue);
        return output;
    }

    private void processFile(String inputFileName) throws IOException{
        Path path = Paths.get(inputFileName);
        if (!Files.exists(path)){
            System.err.println("No such file " + inputFileName + " found. Skipped.");
            return;
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName))){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                processLine(line.trim());
            }
        } catch (IOException e){
            System.err.println("Failed to read " + inputFileName + ": " + e.getMessage());
            throw e;
        }
    }

    private void processLine(String line) throws IOException {
        if (line.isEmpty()) return;

        if (line.matches("-?\\d+")){
            long value = Long.parseLong(line);
            integerPrintQueue.add(value);
        }

        else if (line.matches("-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?")) {
            double value = Double.parseDouble(line);
            doublesPrintQueue.add(value);
        }

        else {
            stringsPrintQueue.add(line);
        }

    }

    public Config getConfiguration() {
        return configuration;
    }

    public List<String> getStringsPrintQueue() {
        return stringsPrintQueue;
    }

    public List<Long> getIntegerPrintQueue() {
        return integerPrintQueue;
    }

    public List<Double> getDoublesPrintQueue() {
        return doublesPrintQueue;
    }

}
