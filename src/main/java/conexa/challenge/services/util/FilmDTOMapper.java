package conexa.challenge.services.util;

import conexa.challenge.model.FilmDTO;

import java.util.List;
import java.util.Map;

public class FilmDTOMapper {

    public static FilmDTO mapFilmDTO(Map<String, Object> properties) {
        return FilmDTO.builder()
                .edited((String) properties.get("edited"))
                .title((String) properties.get("title"))
                .url((String) properties.get("url"))
                .director((String) properties.get("director"))
                .created((String) properties.get("created"))
                .producer((String) properties.get("producer"))
                .releaseDate((String) properties.get("release_date"))
                .episodeId(properties.get("episode_id") != null ? Integer.parseInt(properties.get("episode_id").toString()) : null)
                .openingCrawl((String) properties.get("opening_crawl"))
                .vehicles((List<String>) properties.get("vehicles"))
                .characters((List<String>) properties.get("characters"))
                .species((List<String>) properties.get("species"))
                .planets((List<String>) properties.get("planets"))
                .starships((List<String>) properties.get("starships"))
                .build();
    }
}
