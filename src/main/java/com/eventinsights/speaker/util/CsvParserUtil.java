package com.eventinsights.speaker.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for parsing CSV files using Apache Commons CSV.
 */
public final class CsvParserUtil {

    /**
     * Functional interface used to convert a CSVRecord into a type-safe object.
     *
     * @param <T> type to convert the CSV row into
     */
    @FunctionalInterface
    public interface CsvRecordMapper<T> {
        T map(CSVRecord record);
    }

    // Prevent instantiation
    private CsvParserUtil() {
    }

    /**
     * Parses a CSV file located at the given csvFilePath and maps each row into a custom object.
     *
     * @param csvFilePath   csvFilePath to the CSV file
     * @param mapper mapping function converting a CSVRecord into an object of type T
     * @param <T>    generic type of the mapped objects
     * @return a list of mapped objects, one for each CSV row
     * @throws IOException if the file cannot be read or parsed
     */
    public static <T> List<T> parseFromCsv(String csvFilePath, CsvRecordMapper<T> mapper) throws IOException {
        if (csvFilePath == null || csvFilePath.isBlank()) {
            throw new IllegalArgumentException("CSV file csvFilePath must not be null or empty");
        }

        List<T> records = new ArrayList<>();

        CSVFormat format = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreSurroundingSpaces()
                .withQuote('"')
                .withTrim();

        try (FileReader reader = new FileReader(csvFilePath, StandardCharsets.UTF_8);
             CSVParser parser = format.parse(reader)) {

            for (CSVRecord csvRecord : parser) {
                records.add(mapper.map(csvRecord));
            }
        }

        return records;
    }
}