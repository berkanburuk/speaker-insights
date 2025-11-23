package com.eventinsights.speaker.controller;

import com.eventinsights.speaker.dto.YearlyMostInfluentialSpeakerDto;
import com.eventinsights.speaker.dto.ConferenceSpeakerInfluenceDto;
import com.eventinsights.speaker.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
@RequiredArgsConstructor
public class SpeakerController {

    private final AuthorService authorService;

    @GetMapping("/influence")
    public ResponseEntity<List<ConferenceSpeakerInfluenceDto>> influencialSpeakers() {
        return ResponseEntity.ok(authorService.listInfluentialSpeakers());
    }

    @GetMapping("/influence-yearly")
    public ResponseEntity<List<YearlyMostInfluentialSpeakerDto>> findMostInfluentialSpeakerPerYear() {
        return ResponseEntity.ok(authorService.findMostInfluentialSpeakerPerYear());
    }


}
