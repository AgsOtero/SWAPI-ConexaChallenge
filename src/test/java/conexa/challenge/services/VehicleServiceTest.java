package conexa.challenge.services;

import conexa.challenge.model.VehicleDTO;
import conexa.challenge.model.VehicleListDTO;
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
class VehicleServiceTest {

    @Mock
    private HttpRequestUtil httpRequestUtil;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Test
    void getVehicles() {
        int page = 1;
        int limit = 5;
        Map<String, Object> body = createVehicleListResponse();

        ResponseEntity<Map<String, Object>> mockResponseEntity = ResponseEntity.ok(body);
        when(httpRequestUtil.makeGetRequest(eq("vehicles/?page=" + page + "&limit=" + limit), any(ParameterizedTypeReference.class)))
                .thenReturn(mockResponseEntity);

        VehicleListDTO vehicleListDTO = vehicleService.getVehicles(page, limit);

        assertNotNull(vehicleListDTO);
        assertEquals("nextPageUrl", vehicleListDTO.getNext());
        assertEquals("previousPageUrl", vehicleListDTO.getPrevious());
        assertEquals(3, vehicleListDTO.getTotalPages());
        assertEquals(30, vehicleListDTO.getTotalRecords());
        assertNotNull(vehicleListDTO.getVehicleListResults());
        assertEquals(5, vehicleListDTO.getVehicleListResults().size());
    }

    @Test
    void getVehicleById() {
        // Using valid ID for Vehicle, 4 in this case
        int vehicleId = 4;
        Map<String, Object> body = createVehicleResponse();

        ResponseEntity<Map<String, Object>> mockResponseEntity = ResponseEntity.ok(body);
        when(httpRequestUtil.makeGetRequest(eq("vehicles/" + vehicleId), any(ParameterizedTypeReference.class)))
                .thenReturn(mockResponseEntity);

        Map<String, Object> properties = TestUtil.extractProperties(body);
        when(httpRequestUtil.getObjectMap(body)).thenReturn(properties);

        VehicleDTO vehicleDTO = vehicleService.getVehicleById(vehicleId);

        assertNotNull(vehicleDTO);
        assertEquals("Speeder", vehicleDTO.getName());
        assertEquals("74-Z speeder bike", vehicleDTO.getModel());
    }

    @Test
    public void testGetVehicleByName() {
        String name = "Speeder";
        Map<String, Object> body = createVehicleResponse();

        ResponseEntity<Map<String, Object>> mockResponseEntity = ResponseEntity.ok(body);
        when(httpRequestUtil.makeGetRequest(eq("vehicles/?name=" + name), any(ParameterizedTypeReference.class)))
                .thenReturn(mockResponseEntity);

        Map<String, Object> properties = TestUtil.extractProperties(body);
        when(httpRequestUtil.getListMap(body)).thenReturn(properties);

        VehicleDTO vehicleDTO = vehicleService.getVehicleByName(name);

        assertNotNull(vehicleDTO);
        assertEquals("Speeder", vehicleDTO.getName());
        assertEquals("74-Z speeder bike", vehicleDTO.getModel());
    }

    @Test
    public void testGetVehicleByModel() {
        String model = "74-Z speeder bike";
        Map<String, Object> body = createVehicleResponse();

        ResponseEntity<Map<String, Object>> mockResponseEntity = ResponseEntity.ok(body);
        when(httpRequestUtil.makeGetRequest(eq("vehicles/?model=" + model), any(ParameterizedTypeReference.class)))
                .thenReturn(mockResponseEntity);

        Map<String, Object> properties = TestUtil.extractProperties(body);
        when(httpRequestUtil.getListMap(body)).thenReturn(properties);

        VehicleDTO vehicleDTO = vehicleService.getVehicleByModel(model);

        assertNotNull(vehicleDTO);
        assertEquals("Speeder", vehicleDTO.getName());
        assertEquals("74-Z speeder bike", vehicleDTO.getModel());
    }

    private Map<String, Object> createVehicleListResponse() {
        List<Map<String, Object>> results = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Map<String, Object> vehicle = new HashMap<>();
            vehicle.put("name", "Vehicle" + i);
            vehicle.put("model", "Model" + i);
            vehicle.put("uid", i);
            vehicle.put("url", "url" + i);
            results.add(vehicle);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("next", "nextPageUrl");
        body.put("previous", "previousPageUrl");
        body.put("total_pages", 3);
        body.put("total_records", 30);
        body.put("results", results);

        return body;
    }

    private Map<String, Object> createVehicleResponse() {
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMap.put("name", "Speeder");
        propertiesMap.put("model", "74-Z speeder bike");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("properties", propertiesMap);

        Map<String, Object> body = new HashMap<>();
        body.put("result", resultMap);

        return body;
    }

}