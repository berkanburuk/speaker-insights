package com.eventinsights.speaker.service;

import com.eventinsights.speaker.dto.ConferenceSpeakerInfluenceDto;
import com.eventinsights.speaker.dto.YearlyMostInfluentialSpeakerDto;
import com.eventinsights.speaker.repository.AuthorRepository;
import com.eventinsights.speaker.repository.ConferenceDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final ConferenceDetailRepository conferenceDetailRepository;

    @Transactional
    public List<ConferenceSpeakerInfluenceDto> listInfluentialSpeakers() {
        return authorRepository.findSpeakersWithInfluenceScore()
                .stream()
                .map(p -> new ConferenceSpeakerInfluenceDto(
                        p.getName(),
                        p.getViews(),
                        p.getLikes(),
                        p.getDate(),
                        p.getInfluenceScore()
                ))
                .toList();
    }

    @Transactional
    public List<YearlyMostInfluentialSpeakerDto> findMostInfluentialSpeakerPerYear() {
        return authorRepository.findMostInfluentialSpeakerPerYear()
                .stream()
                .map(p -> new YearlyMostInfluentialSpeakerDto(
                        p.getName(),
                        p.getYear(),
                        p.getTotalInfluence()
                ))
                .toList();
    }


}
