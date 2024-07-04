package conexa.challenge.services;

import conexa.challenge.model.VehicleDTO;
import conexa.challenge.model.VehicleListDTO;
import conexa.challenge.services.util.HttpRequestUtil;
import conexa.challenge.services.util.VehicleDTOMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final HttpRequestUtil httpRequestUtil;

    public VehicleServiceImpl(HttpRequestUtil httpRequestUtil) {
        this.httpRequestUtil = httpRequestUtil;
    }

    @Override
    public VehicleListDTO getVehicles(int page, int limit) {
        ResponseEntity<Map<String, Object>> response =
                httpRequestUtil.makeGetRequest("vehicles/?page=" + page + "&limit=" + limit,
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        });

        Map<String, Object> body = response.getBody();
        if (body == null) {
            return null;
        }

        return VehicleListDTO.builder()
                .next((String) body.get("next"))
                .previous((String) body.get("previous"))
                .totalPages((int) body.get("total_pages"))
                .totalRecords((int) body.get("total_records"))
                .vehicleListResults(HttpRequestUtil.getCommonListResult(body)).
                build();
    }


    @Override
    public VehicleDTO getVehicleById(int id) {
        ResponseEntity<Map<String, Object>> response =
                httpRequestUtil.makeGetRequest("vehicles/" + id,
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        });
        return getVehicleDTO(response, null);
    }

    @Override
    public VehicleDTO getVehicleByName(String name) {
        ResponseEntity<Map<String, Object>> response =
                httpRequestUtil.makeGetRequest("vehicles/?name=" + name,
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        });
        return getVehicleDTO(response, name);
    }

    @Override
    public VehicleDTO getVehicleByModel(String model) {
        ResponseEntity<Map<String, Object>> response =
                httpRequestUtil.makeGetRequest("vehicles/?model=" + model,
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        });
        return getVehicleDTO(response, model);
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
    private VehicleDTO getVehicleDTO(ResponseEntity<Map<String, Object>> response, String modelOrName) {
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
        return VehicleDTOMapper.mapVehicleDTO(properties);
    }

}
