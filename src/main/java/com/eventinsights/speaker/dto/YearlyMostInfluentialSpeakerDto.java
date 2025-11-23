package com.eventinsights.speaker.dto;

/**
 * DTO representing the most influential speaker for a given year.
 *
 * @param name            the speaker's name
 * @param year            the year of influence
 * @param influenceScore  calculated influence score
 */
public record YearlyMostInfluentialSpeakerDto(String name, Integer year, double influenceScore) {
}
