package conexa.challenge.controller;

import conexa.challenge.model.CommonListResult;
import conexa.challenge.model.PeopleDTO;
import conexa.challenge.model.PeopleListDTO;
import conexa.challenge.services.PeopleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeopleService peopleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar los mocks para devolver datos de prueba
        CommonListResult result1 = CommonListResult.builder()
                .uid(1)
                .name("Person1")
                .url("/people/1")
                .build();
        CommonListResult result2 = CommonListResult.builder()
                .uid(2)
                .name("Person2")
                .url("/people/2")
                .build();
        List<CommonListResult> results = Arrays.asList(result1, result2);
        PeopleListDTO peopleListDTO = PeopleListDTO.builder()
                .totalRecords(2)
                .totalPages(1)
                .previous(null)
                .next(null)
                .commonListResult(results)
                .build();

        PeopleDTO person1 = PeopleDTO.builder()
                .name("Person1")
                .birthYear("2000")
                .eyeColor("blue")
                .gender("male")
                .hairColor("blonde")
                .height("180")
                .mass("75")
                .skinColor("fair")
                .homeworld("Earth")
                .url("/people/1")
                .created("2020-01-01")
                .edited("2020-01-01")
                .build();
        PeopleDTO person2 = PeopleDTO.builder()
                .name("Person2")
                .birthYear("1990")
                .eyeColor("green")
                .gender("female")
                .hairColor("brown")
                .height("170")
                .mass("65")
                .skinColor("light")
                .homeworld("Mars")
                .url("/people/2")
                .created("2020-01-01")
                .edited("2020-01-01")
                .build();

        when(peopleService.getPeople(1, 10)).thenReturn(peopleListDTO);
        when(peopleService.getPeopleById(1)).thenReturn(person1);
        when(peopleService.getPeopleByName("Person1")).thenReturn(person1);
    }

    @Test
    public void testGetAllPeople() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/people")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRecords", is(2)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.previous").isEmpty())
                .andExpect(jsonPath("$.next").isEmpty())
                .andExpect(jsonPath("$.commonListResult", hasSize(2)))
                .andExpect(jsonPath("$.commonListResult[0].uid", is(1)))
                .andExpect(jsonPath("$.commonListResult[0].name", is("Person1")))
                .andExpect(jsonPath("$.commonListResult[0].url", is("/people/1")))
                .andExpect(jsonPath("$.commonListResult[1].uid", is(2)))
                .andExpect(jsonPath("$.commonListResult[1].name", is("Person2")))
                .andExpect(jsonPath("$.commonListResult[1].url", is("/people/2")));
    }

    @Test
    public void testGetPeopleById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/people/id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Person1")))
                .andExpect(jsonPath("$.birthYear", is("2000")))
                .andExpect(jsonPath("$.eyeColor", is("blue")))
                .andExpect(jsonPath("$.gender", is("male")))
                .andExpect(jsonPath("$.hairColor", is("blonde")))
                .andExpect(jsonPath("$.height", is("180")))
                .andExpect(jsonPath("$.mass", is("75")))
                .andExpect(jsonPath("$.skinColor", is("fair")))
                .andExpect(jsonPath("$.homeworld", is("Earth")))
                .andExpect(jsonPath("$.url", is("/people/1")))
                .andExpect(jsonPath("$.created", is("2020-01-01")))
                .andExpect(jsonPath("$.edited", is("2020-01-01")));
    }

    @Test
    public void testGetPeopleByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/people/name/Person1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Person1")))
                .andExpect(jsonPath("$.birthYear", is("2000")))
                .andExpect(jsonPath("$.eyeColor", is("blue")))
                .andExpect(jsonPath("$.gender", is("male")))
                .andExpect(jsonPath("$.hairColor", is("blonde")))
                .andExpect(jsonPath("$.height", is("180")))
                .andExpect(jsonPath("$.mass", is("75")))
                .andExpect(jsonPath("$.skinColor", is("fair")))
                .andExpect(jsonPath("$.homeworld", is("Earth")))
                .andExpect(jsonPath("$.url", is("/people/1")))
                .andExpect(jsonPath("$.created", is("2020-01-01")))
                .andExpect(jsonPath("$.edited", is("2020-01-01")));
    }
}
