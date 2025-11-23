package com.eventinsights.speaker;

import com.eventinsights.speaker.config.BatchCsvData;
import com.eventinsights.speaker.dto.ConferenceSpeakerInfluenceDto;
import com.eventinsights.speaker.dto.YearlyMostInfluentialSpeakerDto;
import com.eventinsights.speaker.service.AuthorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
class AuthorControllerInsightsApplicationTests {

    @Autowired
    AuthorService authorService;

    @Autowired
    BatchCsvData batchCsvData;

    @BeforeAll
    void setup() {
        batchCsvData.processCsvBatch();
    }

    @Test
    @DisplayName("Should return influential speakers in correct order")
    void shouldReturnInfluentialSpeakersInCorrectOrder() {
        List<String> actualNames = authorService.listInfluentialSpeakers()
                .stream()
                .map(ConferenceSpeakerInfluenceDto::name)
                .toList();

        List<String> expected = List.of(
                "Steven Sharp Nelson",
                "Jota Samper",
                "Ozawa Bineshi Albert",
                "Gay Gordon-Byrne",
                "Sydney Iaukea",
                "Erin Meezan",
                "Let It Happen",
                "Dave Matthews"
        );

        assertThat(actualNames).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Should return most influential speaker per year")
    void shouldReturnMostInfluentialSpeakerPerYear() {
        List<String> actualNames = authorService.findMostInfluentialSpeakerPerYear()
                .stream()
                .map(YearlyMostInfluentialSpeakerDto::name)
                .toList();

        List<String> expected = List.of(
                "Sydney Iaukea",
                "Ozawa Bineshi Albert",
                "Steven Sharp Nelson"
        );

        assertThat(actualNames).containsExactlyElementsOf(expected);
    }
}
