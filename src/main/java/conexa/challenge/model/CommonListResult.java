package conexa.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some calls return a List of different entities that share the same values, so this is the representation
 * for example Vehicles, Starships and People share this list
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonListResult {

    private int uid;
    private String name;
    private String url;

}
