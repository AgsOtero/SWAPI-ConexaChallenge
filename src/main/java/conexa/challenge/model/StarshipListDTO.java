package conexa.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StarshipListDTO {
//TODO refactor this too
    private int totalRecords;
    private int totalPages;
    private String previous;
    private String next;
    private List<CommonListResult> starshipListResults;

}