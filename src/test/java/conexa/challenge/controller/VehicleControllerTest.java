package conexa.challenge.controller;

import conexa.challenge.model.CommonListResult;
import conexa.challenge.model.VehicleDTO;
import conexa.challenge.model.VehicleListDTO;
import conexa.challenge.services.VehicleService;
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
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        CommonListResult result1 = CommonListResult.builder()
                .uid(1)
                .name("Vehicle1")
                .url("/vehicles/1")
                .build();
        CommonListResult result2 = CommonListResult.builder()
                .uid(2)
                .name("Vehicle2")
                .url("/vehicles/2")
                .build();
        List<CommonListResult> results = Arrays.asList(result1, result2);
        VehicleListDTO vehicleListDTO = VehicleListDTO.builder()
                .totalRecords(2)
                .totalPages(1)
                .previous(null)
                .next(null)
                .vehicleListResults(results)
                .build();

        VehicleDTO vehicle1 = VehicleDTO.builder()
                .id(1)
                .name("Vehicle1")
                .model("Model1")
                .build();

        when(vehicleService.getVehicles(1, 10)).thenReturn(vehicleListDTO);
        when(vehicleService.getVehicleById(1)).thenReturn(vehicle1);
        when(vehicleService.getVehicleByName("Vehicle1")).thenReturn(vehicle1);
        when(vehicleService.getVehicleByModel("Model1")).thenReturn(vehicle1);
    }

    @Test
    public void testGetVehicles() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicle")
                        .param("page", "1")
                        .param("limit", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRecords", is(2)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.previous").isEmpty())
                .andExpect(jsonPath("$.next").isEmpty())
                .andExpect(jsonPath("$.vehicleListResults", hasSize(2)))
                .andExpect(jsonPath("$.vehicleListResults[0].uid", is(1)))
                .andExpect(jsonPath("$.vehicleListResults[0].name", is("Vehicle1")))
                .andExpect(jsonPath("$.vehicleListResults[0].url", is("/vehicles/1")))
                .andExpect(jsonPath("$.vehicleListResults[1].uid", is(2)))
                .andExpect(jsonPath("$.vehicleListResults[1].name", is("Vehicle2")))
                .andExpect(jsonPath("$.vehicleListResults[1].url", is("/vehicles/2")));
    }

    @Test
    public void testGetVehicleById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicle/id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Vehicle1")))
                .andExpect(jsonPath("$.model", is("Model1")));
    }

    @Test
    public void testGetVehicleByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicle/name/Vehicle1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Vehicle1")))
                .andExpect(jsonPath("$.model", is("Model1")));
    }

    @Test
    public void testGetVehicleByModel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicle/model/Model1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Vehicle1")))
                .andExpect(jsonPath("$.model", is("Model1")));
    }
}