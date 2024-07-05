package conexa.challenge.services;

import conexa.challenge.model.StarshipDTO;
import conexa.challenge.model.StarshipListDTO;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class StarshipServiceTest {

    @Mock
    private HttpRequestUtil httpRequestUtil;

    @InjectMocks
    private StarshipServiceImpl starshipService;

    @Test
    public void testGetStarships() {
        int page = 1;
        int limit = 5;
        Map<String, Object> body = createStarshipListResponse();

        ResponseEntity<Map<String, Object>> mockResponseEntity = ResponseEntity.ok(body);
        when(httpRequestUtil.makeGetRequest(eq("starships/?page=" + page + "&limit=" + limit), any(ParameterizedTypeReference.class)))
                .thenReturn(mockResponseEntity);

        StarshipListDTO starshipListDTO = starshipService.getStarships(page, limit);

        assertNotNull(starshipListDTO);
        assertEquals("nextPageUrl", starshipListDTO.getNext());
        assertEquals("previousPageUrl", starshipListDTO.getPrevious());
        assertEquals(3, starshipListDTO.getTotalPages());
        assertEquals(30, starshipListDTO.getTotalRecords());
        assertNotNull(starshipListDTO.getStarshipListResults());
        assertEquals(5, starshipListDTO.getStarshipListResults().size());
    }

    @Test
    public void testGetStarshipById() {
        //Valid ID for Starship, 1 won't work and will be used for test the failed ones
        int shipId = 2;
        Map<String, Object> body = createStarShipResponse();

        ResponseEntity<Map<String, Object>> mockResponseEntity = ResponseEntity.ok(body);
        when(httpRequestUtil.makeGetRequest(eq("starships/" + shipId), any(ParameterizedTypeReference.class)))
                .thenReturn(mockResponseEntity);

        Map<String, Object> properties = TestUtil.extractProperties(body);
        when(httpRequestUtil.getObjectMap(body)).thenReturn(properties);

        StarshipDTO starshipDTO = starshipService.getStarshipById(shipId);

        assertNotNull(starshipDTO);
        assertEquals("X-Wing", starshipDTO.getName());
        assertEquals("T-65 X-wing starfighter", starshipDTO.getModel());
    }

    @Test
    public void testGetStarshipByName() {
        String name = "X-Wing";
        Map<String, Object> body = createStarShipResponse();

        ResponseEntity<Map<String, Object>> mockResponseEntity = ResponseEntity.ok(body);
        when(httpRequestUtil.makeGetRequest(eq("starships/?name=" + name), any(ParameterizedTypeReference.class)))
                .thenReturn(mockResponseEntity);

        Map<String, Object> properties = TestUtil.extractProperties(body);
        when(httpRequestUtil.getListMap(body)).thenReturn(properties);

        StarshipDTO starshipDTO = starshipService.getStarshipByName(name);

        assertNotNull(starshipDTO);
        assertEquals("X-Wing", starshipDTO.getName());
        assertEquals("T-65 X-wing starfighter", starshipDTO.getModel());
    }

    @Test
    public void testGetStarshipByModel() {
        String model = "T-65 X-wing starfighter";
        Map<String, Object> body = createStarShipResponse();

        ResponseEntity<Map<String, Object>> mockResponseEntity = ResponseEntity.ok(body);
        when(httpRequestUtil.makeGetRequest(eq("starships/?model=" + model), any(ParameterizedTypeReference.class)))
                .thenReturn(mockResponseEntity);

        Map<String, Object> properties = TestUtil.extractProperties(body);
        when(httpRequestUtil.getListMap(body)).thenReturn(properties);

        StarshipDTO starshipDTO = starshipService.getStarshipByModel(model);

        assertNotNull(starshipDTO);
        assertEquals("T-65 X-wing starfighter", starshipDTO.getModel());
        assertEquals("X-Wing", starshipDTO.getName());
    }

    @Test
    void testGetStarshipByIdWhenHttpRequestFails() {
        //Invalid ID for Starship
        int shipId = 1;

        when(httpRequestUtil.makeGetRequest(eq("starships/" + shipId), any(ParameterizedTypeReference.class)))
                .thenThrow(new RuntimeException("Failed request"));

        assertThrows(RuntimeException.class, () -> starshipService.getStarshipById(shipId));
    }

    @Test
    void testGetStarshipByNameWhenHttpRequestFails() {
        String shipName = "Nonexistent";

        when(httpRequestUtil.makeGetRequest(eq("starships?name=" + shipName), any(ParameterizedTypeReference.class)))
                .thenThrow(new RuntimeException("Failed request"));

        assertThrows(RuntimeException.class, () -> starshipService.getStarshipByName(shipName));
    }

    @Test
    void testGetStarshipByModelWhenHttpRequestFails() {
        String shipModel = "Nonexistent";

        when(httpRequestUtil.makeGetRequest(eq("starships?name=" + shipModel), any(ParameterizedTypeReference.class)))
                .thenThrow(new RuntimeException("Failed request"));

        assertThrows(RuntimeException.class, () -> starshipService.getStarshipByModel(shipModel));
    }


    private Map<String, Object> createStarShipResponse() {
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMap.put("name", "X-Wing");
        propertiesMap.put("model", "T-65 X-wing starfighter");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("properties", propertiesMap);

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("result", resultMap);

        return bodyMap;
    }

    private Map<String, Object> createStarshipListResponse() {
        List<Map<String, Object>> results = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Map<String, Object> starship = new HashMap<>();
            starship.put("name", "Starship" + i);
            starship.put("model", "Model" + i);
            starship.put("uid", i);
            starship.put("url", "url" + i);
            results.add(starship);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("next", "nextPageUrl");
        body.put("previous", "previousPageUrl");
        body.put("total_pages", 3);
        body.put("total_records", 30);
        body.put("results", results);

        return body;
    }
}