package conexa.challenge.services;

import conexa.challenge.model.StarshipDTO;
import conexa.challenge.model.StarshipListDTO;
import conexa.challenge.services.util.HttpRequestUtil;
import conexa.challenge.services.util.StarshipDTOMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StarshipServiceImpl implements StarshipService {

    private final HttpRequestUtil httpRequestUtil;

    public StarshipServiceImpl(HttpRequestUtil httpRequestUtil) {
        this.httpRequestUtil = httpRequestUtil;
    }


    @Override
    public StarshipListDTO getStarships(int page, int limit) {

        ResponseEntity<Map<String, Object>> response =
                httpRequestUtil.makeGetRequest("starships/?page=" + page + "&limit=" + limit,
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        });

        Map<String, Object> body = response.getBody();
        if (body == null) {
            return null;
        }

        return StarshipListDTO.builder()
                .next((String) body.get("next"))
                .previous((String) body.get("previous"))
                .totalPages((int) body.get("total_pages"))
                .totalRecords((int) body.get("total_records"))
                .starshipListResults(HttpRequestUtil.getCommonListResult(body)).
                build();
    }

    @Override
    public StarshipDTO getStarshipById(int id) {
        try {
            ResponseEntity<Map<String, Object>> response =
                    httpRequestUtil.makeGetRequest("starships/" + id,
                            new ParameterizedTypeReference<Map<String, Object>>() {
                            });
            return getStarshipDTO(response, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve starship with id " + id, e);
        }
    }

    @Override
    public StarshipDTO getStarshipByName(String starshipName) {
        ResponseEntity<Map<String, Object>> response =
                httpRequestUtil.makeGetRequest("starships/?name=" + starshipName,
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        });
        return getStarshipDTO(response, starshipName);
    }

    @Override
    public StarshipDTO getStarshipByModel(String model) {
        ResponseEntity<Map<String, Object>> response =
                httpRequestUtil.makeGetRequest("starships/?model=" + model,
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        });
        return getStarshipDTO(response, model);
    }

    /**
     * This method was extracted to avoid duplicated code all over the calls,
     * checks for if the body of the response is null and then gets the properties
     * to map them in the DTO class.
     * <p>
     * Also, the response from the API changes if I use a parameter in the URL or not,
     * you will notice that if there's title, it returns a list, and if not an array
     *
     * @param response    **
     * @param modelOrName **
     */
    private StarshipDTO getStarshipDTO(ResponseEntity<Map<String, Object>> response, String modelOrName) {
        Map<String, Object> body = response.getBody();
        if (body == null) {
            return null;
        }
        Map<String, Object> properties;
        if (modelOrName != null) {
            properties = httpRequestUtil.getListMap(body);
            assert properties != null;
        } else {
            properties = httpRequestUtil.getObjectMap(body);
        }
        return StarshipDTOMapper.mapStarshipDTO(properties);
    }
}

