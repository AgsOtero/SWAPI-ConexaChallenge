package conexa.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeopleListDTO {


    private int total_records;
    private int total_pages;
    private String previous;
    private String next;
    private List<PeopleListResult> peopleListResult;

}
