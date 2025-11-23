package com.eventinsights.speaker.dto;

public interface ConferenceSpeakerInfluenceProjection {
    String getName();
    long getViews();
    long getLikes();
    String getDate();
    double getInfluenceScore();
}