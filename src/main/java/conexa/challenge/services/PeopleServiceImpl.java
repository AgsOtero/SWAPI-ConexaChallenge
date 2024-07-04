package conexa.challenge.services;

import conexa.challenge.model.PeopleDTO;
import conexa.challenge.model.PeopleListDTO;
import conexa.challenge.services.util.HttpRequestUtil;
import conexa.challenge.services.util.PeopleDTOMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PeopleServiceImpl implements PeopleService {

    private final HttpRequestUtil httpRequestUtil;

    public PeopleServiceImpl(HttpRequestUtil httpRequestUtil) {
        this.httpRequestUtil = httpRequestUtil;
    }

    @Override
    public PeopleListDTO getPeople(Integer page, Integer limit) {
        ResponseEntity<Map<String, Object>> response =
                httpRequestUtil.makeGetRequest("people/?page=" + page + "&limit=" + limit,
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        });

        Map<String, Object> body = response.getBody();
        if (body == null) {
            return null;
        }
        return PeopleListDTO.builder()
                .next((String) body.get("next"))
                .previous((String) body.get("previous"))
                .totalPages((int) body.get("total_pages"))
                .totalRecords((int) body.get("total_records"))
                .commonListResult(HttpRequestUtil.getCommonListResult(body))
                .build();
    }

    @Override
    public PeopleDTO getPeopleById(int id) {
        ResponseEntity<Map<String, Object>> response =
                httpRequestUtil.makeGetRequest("people/" + id,
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        });
        return getPeopleDTO(response, null);
    }

    @Override
    public PeopleDTO getPeopleByName(String name) {
        ResponseEntity<Map<String, Object>> response =
                httpRequestUtil.makeGetRequest("people/?name=" + name,
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        });
        return getPeopleDTO(response, name);
    }


    private PeopleDTO getPeopleDTO(ResponseEntity<Map<String, Object>> response, String name) {
        Map<String, Object> body = response.getBody();
        if (body == null) {
            return null;
        }
        Map<String, Object> properties;
        if (name != null) {
            properties = httpRequestUtil.getListMap(body);
            assert properties != null;
        } else {
            properties = httpRequestUtil.getObjectMap(body);
        }
        return PeopleDTOMapper.mapPeopleDTO(properties);
    }
}


