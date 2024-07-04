package conexa.challenge.services;

import conexa.challenge.model.FilmDTO;
import conexa.challenge.services.util.FilmDTOMapper;
import conexa.challenge.services.util.HttpRequestUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class FilmServiceImpl implements FilmService {

    @Override
    public List<FilmDTO> getAllFilms() {
        ResponseEntity<Map<String, Object>> response =
                HttpRequestUtil.makeGetRequest("films",
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        });

        Map<String, Object> body = response.getBody();

        List<Map<String, Object>> resultList = (List<Map<String, Object>>) body.get("result");
        if (resultList.isEmpty()) {
            return Collections.emptyList();
        }
        List<FilmDTO> filmDTOList = new ArrayList<>();
        for (Map<String, Object> result : resultList) {
            Map<String, Object> properties = (Map<String, Object>) result.get("properties");
            if (properties.isEmpty()) {
                return Collections.emptyList();
            }
            FilmDTO filmDTO = FilmDTOMapper.mapFilmDTO(properties);
            filmDTOList.add(filmDTO);
        }
        return filmDTOList;
    }

    @Override
    public FilmDTO getFilmById(int id) {

        ResponseEntity<Map<String, Object>> response =
                HttpRequestUtil.makeGetRequest("films/" + id,
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        });

        return getFilmDTO(response, null);
    }

    @Override
    public FilmDTO getFilmByTitle(String title) {

        ResponseEntity<Map<String, Object>> response =
                HttpRequestUtil.makeGetRequest("films/?title=" + title,
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        });
        return getFilmDTO(response, title);
    }


    /**
     * This method was extracted to avoid duplicated code all over the calls,
     * checks for if the body of the response is null and then gets the properties
     * to map them in the DTO class.
     *
     * Also, the response from the API changes if I use a parameter in the URL or not,
     * you will notice that if there's title, it returns a list, and if not an array
     *
     * @param response **
     * @param title **
     */
    private FilmDTO getFilmDTO(ResponseEntity<Map<String, Object>> response, String title) {
        Map<String, Object> body = response.getBody();
        if (body == null) {
            return null;
        }
        Map<String, Object> properties;
        if (title != null) {
            properties = HttpRequestUtil.getListMap(body);
            assert properties != null;
        } else {
            properties = HttpRequestUtil.getObjectMap(body);
        }
        return FilmDTOMapper.mapFilmDTO(properties);
    }

}

