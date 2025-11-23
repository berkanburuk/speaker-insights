package com.eventinsights.speaker.config;

import com.eventinsights.speaker.dto.ConferenceDataDto;
import com.eventinsights.speaker.model.Author;
import com.eventinsights.speaker.model.ConferenceDetail;
import com.eventinsights.speaker.repository.AuthorRepository;
import com.eventinsights.speaker.util.ClassLoaderUtil;
import com.eventinsights.speaker.util.CsvParserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class BatchCsvData implements CommandLineRunner {

    private final AuthorRepository authorRepository;

    @Value("${csv.data.conference.file}")
    private String csvFileName;


    @Override
    public void run(String... args) throws Exception {
        processCsvBatch();
    }

    @Transactional
    public void processCsvBatch() {

        try {
            if (authorRepository.count() > 0) {
                log.info("Conference data already exists. Skipping import.");
                return;
            }

            List<ConferenceDataDto> conferenceDatumDtos = getConferenceInfo();

            Map<String, Author> authorMap = new HashMap<>();

            List<ConferenceDetail> conferenceDetailList = new ArrayList<>();

            for (ConferenceDataDto record : conferenceDatumDtos) {
                String authorName = record.author();
                if (Objects.nonNull(authorName) && !authorName.isBlank()) {

                    Author author = authorMap.computeIfAbsent(authorName, n ->
                            Author.builder()
                                    .name(n)
                                    .conferenceDetails(new ArrayList<>())
                                    .build()
                    );

                    ConferenceDetail detail = ConferenceDetail.builder()
                            .title(record.title().trim())
                            .date(record.date().trim())
                            .likes(record.likes())
                            .views(record.views())
                            .link(record.link().trim())
                            .author(author)
                            .build();

                    conferenceDetailList.add(detail);
                    author.getConferenceDetails().add(detail);
                }
            }

            authorRepository.saveAll(authorMap.values());

        } catch (IOException | URISyntaxException e) {
            log.error("Error processing CSV file", e);
        }
    }

    private List<ConferenceDataDto> getConferenceInfo() throws URISyntaxException, IOException {
        Path path = ClassLoaderUtil.getPath(csvFileName);

        if (path == null) {
            throw new IllegalArgumentException("CSV file not found: " + csvFileName);
        }

        return CsvParserUtil.parseFromCsv(path.toString(), ConferenceDataDto::fromCsvRecord);
    }


}
