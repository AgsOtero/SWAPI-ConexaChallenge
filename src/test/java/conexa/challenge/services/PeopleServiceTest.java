package conexa.challenge.services;

import conexa.challenge.model.PeopleDTO;
import conexa.challenge.model.PeopleListDTO;
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
class PeopleServiceTest {

    @Mock
    private HttpRequestUtil httpRequestUtil;

    @InjectMocks
    private PeopleServiceImpl peopleService;

    @Test
    void getPeople() {
        int page = 1;
        int limit = 5;
        Map<String, Object> body = createPeopleListResponse();

        ResponseEntity<Map<String, Object>> mockResponseEntity = ResponseEntity.ok(body);
        when(httpRequestUtil.makeGetRequest(eq("people/?page=" + page + "&limit=" + limit), any(ParameterizedTypeReference.class)))
                .thenReturn(mockResponseEntity);

        PeopleListDTO peopleListDTO = peopleService.getPeople(page, limit);

        assertNotNull(peopleListDTO);
        assertEquals("nextPageUrl", peopleListDTO.getNext());
        assertEquals("previousPageUrl", peopleListDTO.getPrevious());
        assertEquals(3, peopleListDTO.getTotalPages());
        assertEquals(30, peopleListDTO.getTotalRecords());
        assertNotNull(peopleListDTO.getCommonListResult());
        assertEquals(5, peopleListDTO.getCommonListResult().size());
    }

    @Test
    void getPeopleById() {
        int id = 4;
        Map<String, Object> body = createPeopleResponse();

        ResponseEntity<Map<String, Object>> mockResponseEntity = ResponseEntity.ok(body);
        when(httpRequestUtil.makeGetRequest(eq("people/" + id), any(ParameterizedTypeReference.class)))
                .thenReturn(mockResponseEntity);

        Map<String, Object> properties = TestUtil.extractProperties(body);
        when(httpRequestUtil.getObjectMap(body)).thenReturn(properties);

        PeopleDTO PeopleDTO = peopleService.getPeopleById(id);

        assertNotNull(PeopleDTO);
        assertEquals("Luke Skywalker", PeopleDTO.getName());
        assertEquals("Male", PeopleDTO.getGender());
    }

    @Test
    void getPeopleByName() {
        String name = "Luke Skywalker";
        Map<String, Object> body = createPeopleResponse();

        ResponseEntity<Map<String, Object>> mockResponseEntity = ResponseEntity.ok(body);
        when(httpRequestUtil.makeGetRequest(eq("people/?name=" + name), any(ParameterizedTypeReference.class)))
                .thenReturn(mockResponseEntity);

        Map<String, Object> properties = TestUtil.extractProperties(body);
        when(httpRequestUtil.getListMap(body)).thenReturn(properties);

        PeopleDTO peopleDTO = peopleService.getPeopleByName(name);

        assertNotNull(peopleDTO);
        assertEquals("Luke Skywalker", peopleDTO.getName());
        assertEquals("Male", peopleDTO.getGender());
    }

    private Map<String, Object> createPeopleListResponse() {
        List<Map<String, Object>> results = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Map<String, Object> people = new HashMap<>();
            people.put("name", "people" + i);
            people.put("gender", "Gender" + i);
            people.put("uid", i);
            people.put("url", "url" + i);
            results.add(people);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("next", "nextPageUrl");
        body.put("previous", "previousPageUrl");
        body.put("total_pages", 3);
        body.put("total_records", 30);
        body.put("results", results);

        return body;
    }

    private Map<String, Object> createPeopleResponse() {
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMap.put("name", "Luke Skywalker");
        propertiesMap.put("gender", "Male");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("properties", propertiesMap);

        Map<String, Object> body = new HashMap<>();
        body.put("result", resultMap);

        return body;
    }
}
