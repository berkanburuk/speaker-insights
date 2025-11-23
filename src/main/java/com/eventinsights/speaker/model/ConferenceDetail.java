package com.eventinsights.speaker.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "conference_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConferenceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String date;

    private long views;

    private long likes;

    private String link;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    private Author author;
}