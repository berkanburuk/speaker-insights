package com.eventinsights.speaker.dto;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;

/**
 * Immutable DTO representing a single CSV row of TedTalk data.
 * Provides safe parsing for missing or invalid numeric/string values.
 */
@Slf4j
public record ConferenceDataDto(
        String title,
        String author,
        String date,
        long views,
        long likes,
        String link
) {
    public static ConferenceDataDto fromCsvRecord(CSVRecord record) {
        return new ConferenceDataDto(
                getString(record, "title"),
                getString(record, "author"),
                getString(record, "date"),
                getLong(record, "views"),
                getLong(record, "likes"),
                getString(record, "link")
        );
    }

    private static long getLong(CSVRecord record, String column) {
        try {
            return Long.parseLong(record.get(column).trim());
        } catch (NumberFormatException e) {
            log.warn("Invalid number in CSV column '{}': '{}', defaulting to 0", column, record.get(column));
            return 0L;
        } catch (IllegalArgumentException e) {
            log.warn("Missing column '{}' in CSVRecord, defaulting to 0", column);
            return 0L;
        }
    }
    private static String getString(CSVRecord record, String column) {
        try {
            String value = record.get(column);
            return value != null ? value.trim() : "";
        } catch (IllegalArgumentException e) {
            log.warn("Missing column '{}' in CSVRecord, defaulting to empty string.", column);
            return "";
        }
    }
}