package com.eventinsights.speaker.dto;

/**
 * DTO representing detailed speaker influence per conference.
 */
public record ConferenceSpeakerInfluenceDto(
        String name,
        long views,
        long likes,
        String date,
        double influenceScore
) {
}

