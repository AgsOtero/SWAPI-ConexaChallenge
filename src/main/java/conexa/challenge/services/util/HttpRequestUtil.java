package conexa.challenge.services.util;


import conexa.challenge.model.CommonListResult;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Class to move the logic of the calls outside the service,
 * it's too repetitive and reduces the boilerplate in the calls *  *
 */

@Component
public class HttpRequestUtil {
    private static final String USER_AGENT = "PostmanRuntime/7.32.3";

    private static final String BASE_URL = "https://swapi.tech/api/";

    private static final RestTemplate restTemplate = new RestTemplate();

    public static <T> ResponseEntity<T> makeGetRequest(
            String endpoint,
            ParameterizedTypeReference<T> responseType) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", USER_AGENT);
        headers.set("Accept", "*/*");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + endpoint);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                responseType
        );
    }

    /**
     * This method just gets the properties from the result map
     *
     * @param body
     * @return
     */

    public static Map<String, Object> getObjectMap(Map<String, Object> body) {
        Map<String, Object> result = (Map<String, Object>) body.get("result");
        return (Map<String, Object>) result.get("properties");
    }

    public static Map<String, Object> getListMap(Map<String, Object> body) {
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) body.get("result");
        if (resultList.isEmpty()) {
            return null;
        }
        return (Map<String, Object>) resultList.get(0).get("properties");
    }

    public static List<CommonListResult> getCommonListResult(Map<String,Object> body){
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) body.get("results");
        if (resultList.isEmpty()) {
            return Collections.emptyList();
        }
            List<CommonListResult> commonListResults = new ArrayList<>();
            for (Map<String, Object> result : resultList) {
                CommonListResult commonListResult = new CommonListResult();
                commonListResult.setName((String) result.get("name"));
                commonListResult.setUid(Integer.parseInt(result.get("uid").toString()));
                commonListResult.setUrl((String) result.get("url"));

                commonListResults.add(commonListResult);
            }
            return commonListResults;
    }
}


