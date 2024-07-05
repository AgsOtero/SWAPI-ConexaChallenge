package conexa.challenge.controller;

import conexa.challenge.model.CommonListResult;
import conexa.challenge.model.StarshipDTO;
import conexa.challenge.model.StarshipListDTO;
import conexa.challenge.services.StarshipService;
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
public class StarshipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StarshipService starshipService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        CommonListResult result1 = CommonListResult.builder()
                .uid(1)
                .name("Starship1")
                .url("/starships/1")
                .build();
        CommonListResult result2 = CommonListResult.builder()
                .uid(2)
                .name("Starship2")
                .url("/starships/2")
                .build();
        List<CommonListResult> results = Arrays.asList(result1, result2);
        StarshipListDTO starshipListDTO = StarshipListDTO.builder()
                .totalRecords(2)
                .totalPages(1)
                .previous(null)
                .next(null)
                .starshipListResults(results)
                .build();

        StarshipDTO starship1 = StarshipDTO.builder()
                .id(1)
                .name("Starship1")
                .model("Model1")
                .build();

        when(starshipService.getStarships(1, 10)).thenReturn(starshipListDTO);
        when(starshipService.getStarshipById(1)).thenReturn(starship1);
        when(starshipService.getStarshipByName("Starship1")).thenReturn(starship1);
        when(starshipService.getStarshipByModel("Model1")).thenReturn(starship1);
    }

    @Test
    public void testGetStarships() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/starship")
                        .param("page", "1")
                        .param("limit", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRecords", is(2)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.previous").isEmpty())
                .andExpect(jsonPath("$.next").isEmpty())
                .andExpect(jsonPath("$.starshipListResults", hasSize(2)))
                .andExpect(jsonPath("$.starshipListResults[0].uid", is(1)))
                .andExpect(jsonPath("$.starshipListResults[0].name", is("Starship1")))
                .andExpect(jsonPath("$.starshipListResults[0].url", is("/starships/1")))
                .andExpect(jsonPath("$.starshipListResults[1].uid", is(2)))
                .andExpect(jsonPath("$.starshipListResults[1].name", is("Starship2")))
                .andExpect(jsonPath("$.starshipListResults[1].url", is("/starships/2")));
    }

    @Test
    public void testGetStarshipById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/starship/id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Starship1")))
                .andExpect(jsonPath("$.model", is("Model1")));
    }

    @Test
    public void testGetStarshipByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/starship/name/Starship1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Starship1")))
                .andExpect(jsonPath("$.model", is("Model1")));
    }

    @Test
    public void testGetStarshipByModel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/starship/model/Model1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Starship1")))
                .andExpect(jsonPath("$.model", is("Model1")));
    }
}
