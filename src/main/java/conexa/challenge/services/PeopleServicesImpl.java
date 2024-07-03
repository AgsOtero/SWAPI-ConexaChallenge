package conexa.challenge.services;

import conexa.challenge.model.PeopleDTO;
import conexa.challenge.model.PeopleListDTO;
import conexa.challenge.model.PeopleListResult;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class PeopleServicesImpl implements PeopleService {
    private String apiUrl = "https://swapi.tech/api/";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public PeopleListDTO getPeople(Integer page, Integer size) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "PostmanRuntime/7.32.3");
        headers.set("Accept", "*/*");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        String url = apiUrl + "people/?page=" + page + "&limit=" + size;

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );

        Map<String, Object> body = response.getBody();
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) body.get("results");
        if (!resultList.isEmpty()) {
            List<PeopleListResult> peopleListResults = new ArrayList<>();
            for (Map<String, Object> result : resultList) {
                PeopleListResult peopleListResult = new PeopleListResult();
                peopleListResult.setName((String) result.get("name"));
                peopleListResult.setUid(Integer.parseInt(result.get("uid").toString()));
                peopleListResult.setUrl((String) result.get("url"));

                peopleListResults.add(peopleListResult);
            }
            PeopleListDTO peopleListDTO = new PeopleListDTO();
            peopleListDTO.setNext((String) body.get("next"));
            peopleListDTO.setPrevious((String) body.get("previous"));
            peopleListDTO.setTotal_pages((int) body.get("total_pages"));
            peopleListDTO.setTotal_records((int) body.get("total_records"));
            peopleListDTO.setPeopleListResult(peopleListResults);
            return peopleListDTO;
        }
        return (PeopleListDTO) Collections.emptyList();
    }

    @Override
    public PeopleDTO getPeopleById(int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "PostmanRuntime/7.32.3");
        headers.set("Accept", "*/*");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                apiUrl + "people/" + id,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );

        Map<String, Object> body = response.getBody();
        if (body != null && body.containsKey("result")) {
            Map<String, Object> result = (Map<String, Object>) body.get("result");
            Map<String, Object> properties = (Map<String, Object>) result.get("properties");

            PeopleDTO peopleDTO = new PeopleDTO();
            peopleDTO.setName((String) properties.get("name"));
            peopleDTO.setHeight((String) properties.get("height"));
            peopleDTO.setMass((String) properties.get("mass"));
            peopleDTO.setHair_color((String) properties.get("hair_color"));
            peopleDTO.setSkin_color((String) properties.get("skin_color"));
            peopleDTO.setEye_color((String) properties.get("eye_color"));
            peopleDTO.setBirth_year((String) properties.get("birth_year"));
            peopleDTO.setGender((String) properties.get("gender"));
            peopleDTO.setHomeworld(String.valueOf(properties.get("homeworld")));
            peopleDTO.setUrl((String) properties.get("url"));
            peopleDTO.setCreated((String) properties.get("created"));
            peopleDTO.setEdited((String) properties.get("edited"));

            return peopleDTO;
        }
        //TODO Catch negative values
        return new PeopleDTO();
    }

    @Override
    public PeopleDTO getPeopleByName(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "PostmanRuntime/7.32.3");
        headers.set("Accept", "*/*");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                apiUrl + "people/?name=" + name,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );

        Map<String, Object> body = response.getBody();
        if (body != null && body.containsKey("result")) {
            List<Map<String, Object>> resultList = (List<Map<String, Object>>) body.get("result");
            if (!resultList.isEmpty()) {
                Map<String, Object> properties = (Map<String, Object>) resultList.get(0).get("properties");

                PeopleDTO peopleDTO = new PeopleDTO();
                peopleDTO.setName((String) properties.get("name"));
                peopleDTO.setHeight((String) properties.get("height"));
                peopleDTO.setMass((String) properties.get("mass"));
                peopleDTO.setHair_color((String) properties.get("hair_color"));
                peopleDTO.setSkin_color((String) properties.get("skin_color"));
                peopleDTO.setEye_color((String) properties.get("eye_color"));
                peopleDTO.setBirth_year((String) properties.get("birth_year"));
                peopleDTO.setGender((String) properties.get("gender"));
                peopleDTO.setHomeworld(String.valueOf(properties.get("homeworld")));
                peopleDTO.setUrl((String) properties.get("url"));
                peopleDTO.setCreated((String) properties.get("created"));
                peopleDTO.setEdited((String) properties.get("edited"));

                return peopleDTO;
            }
            //TODO Catch negative values
        }
        return new PeopleDTO();
    }
}


