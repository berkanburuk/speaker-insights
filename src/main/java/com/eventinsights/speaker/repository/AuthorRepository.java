package com.eventinsights.speaker.repository;

import com.eventinsights.speaker.dto.YearlyMostInfluentialSpeakerProjection;
import com.eventinsights.speaker.dto.ConferenceSpeakerInfluenceProjection;
import com.eventinsights.speaker.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(
            value = """
                    SELECT sp.name AS name,
                           cd.views AS views,
                           cd.likes AS likes,
                           cd.date as dates,
                           CASE
                               WHEN cd.views <= 0 THEN 0
                               WHEN cd.likes > cd.views THEN 0
                               ELSE (CAST(cd.likes AS double precision) / cd.views) * LOG10(cd.views)
                           END AS influence_score
                    FROM author sp
                             JOIN conference_detail cd ON sp.id = cd.author_id
                    ORDER BY influence_score DESC
                    """,
            nativeQuery = true
    )
    List<ConferenceSpeakerInfluenceProjection> findSpeakersWithInfluenceScore();

    @Query(
            value = """
                    SELECT sp.name,
                           ranked.year,
                           ranked.total_influence
                    FROM (
                        SELECT
                            yearly.author_id,
                            yearly.year,
                            yearly.total_influence,
                            ROW_NUMBER() OVER (
                                PARTITION BY yearly.year
                                ORDER BY yearly.total_influence DESC
                            ) AS rn
                        FROM (
                            SELECT
                                cd.author_id,
                                EXTRACT(YEAR FROM to_date(initcap(trim(cd.date)), 'FMMonth YYYY')) AS year,
                                SUM(
                                    CASE
                                        WHEN cd.views <= 0 THEN 0
                                        WHEN cd.likes > cd.views THEN 0
                                        ELSE (CAST(cd.likes AS double precision) / cd.views) * LOG10(cd.views)
                                    END
                                ) AS total_influence
                            FROM conference_detail cd
                            GROUP BY cd.author_id,
                                     EXTRACT(YEAR FROM to_date(initcap(trim(cd.date)), 'FMMonth YYYY'))
                        ) yearly
                    ) ranked
                    JOIN author sp ON sp.id = ranked.author_id
                    WHERE ranked.rn = 1
                    ORDER BY ranked.year DESC
                    """,
            nativeQuery = true
    )
    List<YearlyMostInfluentialSpeakerProjection> findMostInfluentialSpeakerPerYear();


}
