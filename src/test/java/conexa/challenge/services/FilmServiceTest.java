package conexa.challenge.services;

import conexa.challenge.model.FilmDTO;
import conexa.challenge.services.util.HttpRequestUtil;
import conexa.challenge.services.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FilmServiceTest {

    @Mock
    private HttpRequestUtil httpRequestUtil;

    @InjectMocks
    private FilmServiceImpl filmService;

    @Test
    void getAllFilms() {
        Map<String, Object> body = createFilmListResponse();

        ResponseEntity<Map<String, Object>> mockResponseEntity = ResponseEntity.ok(body);
        when(httpRequestUtil.makeGetRequest(eq("films"), any(ParameterizedTypeReference.class)))
                .thenReturn(mockResponseEntity);

        List<FilmDTO> filmList = filmService.getAllFilms();

        assertNotNull(filmList);
        assertEquals(5, filmList.size());
    }

    @Test
    void getFilmById() {
        int filmId = 4;
        Map<String, Object> body = createFilmResponse();

        ResponseEntity<Map<String, Object>> mockResponseEntity = ResponseEntity.ok(body);
        when(httpRequestUtil.makeGetRequest(eq("films/" + filmId), any(ParameterizedTypeReference.class)))
                .thenReturn(mockResponseEntity);

        Map<String, Object> properties = TestUtil.extractProperties(body);
        when(httpRequestUtil.getObjectMap(body)).thenReturn(properties);

        FilmDTO filmDTO = filmService.getFilmById(filmId);

        assertNotNull(filmDTO);
        assertEquals("A New Hope", filmDTO.getTitle());
        assertEquals("George Lucas", filmDTO.getDirector());
    }

    @Test
    void getFilmByTitle() {
        String title = "A New Hope";
        Map<String, Object> body = createFilmResponse();

        ResponseEntity<Map<String, Object>> mockResponseEntity = ResponseEntity.ok(body);
        when(httpRequestUtil.makeGetRequest(eq("films/?title=" + title), any(ParameterizedTypeReference.class)))
                .thenReturn(mockResponseEntity);

        Map<String, Object> properties = TestUtil.extractProperties(body);
        when(httpRequestUtil.getListMap(body)).thenReturn(properties);

        FilmDTO filmDTO = filmService.getFilmByTitle(title);

        assertNotNull(filmDTO);
        assertEquals("A New Hope", filmDTO.getTitle());
        assertEquals("George Lucas", filmDTO.getDirector());
    }

    private Map<String, Object> createFilmListResponse() {
        List<Map<String, Object>> results = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Map<String, Object> properties = new HashMap<>();
            properties.put("title", "Film" + i);
            properties.put("director", "Director" + i);

            Map<String, Object> film = new HashMap<>();
            film.put("properties", properties);
            results.add(film);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("result", results);

        return body;
    }

    private Map<String, Object> createFilmResponse() {
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMap.put("title", "A New Hope");
        propertiesMap.put("director", "George Lucas");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("properties", propertiesMap);

        Map<String, Object> body = new HashMap<>();
        body.put("result", resultMap);

        return body;
    }

}
