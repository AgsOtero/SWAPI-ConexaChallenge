package conexa.challenge.services.utils;

import java.util.Map;

public class TestUtil {

    // Extracts and returns the properties map nested under the "result" key.
    public static Map<String, Object> extractProperties(Map<String, Object> body) {
        Map<String, Object> result = (Map<String, Object>) body.get("result");
        return (Map<String, Object>) result.get("properties");
    }

}
