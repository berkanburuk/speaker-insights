package com.eventinsights.speaker.repository;

import com.eventinsights.speaker.model.ConferenceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceDetailRepository extends JpaRepository<ConferenceDetail, Long> {
}
