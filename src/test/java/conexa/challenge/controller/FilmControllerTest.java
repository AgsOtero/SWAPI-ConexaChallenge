package conexa.challenge.controller;

import conexa.challenge.model.FilmDTO;
import conexa.challenge.services.FilmService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        FilmDTO film1 = FilmDTO.builder()
                .title("Film1")
                .director("Director1")
                .build();
        FilmDTO film2 = FilmDTO.builder()
                .title("Film2")
                .director("Director2")
                .build();
        List<FilmDTO> films = Arrays.asList(film1, film2);

        when(filmService.getAllFilms()).thenReturn(films);
    }

    @Test
    public void testGetFilms() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/film")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())  // Esto imprimirá la respuesta para depuración
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", is("Film1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].director", is("Director1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", is("Film2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].director", is("Director2")));
    }

    @Test
    public void getFilmsById() throws Exception {
        FilmDTO film = FilmDTO.builder()
                .title("A New Hope")
                .director("George Lucas")
                .build();

        when(filmService.getFilmById(anyInt())).thenReturn(film);

        mockMvc.perform(get("/api/v1/film/id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("A New Hope"))
                .andExpect(jsonPath("$.director").value("George Lucas"));
    }

    @Test
    void getFilmsByTitle() throws Exception {
        FilmDTO film = FilmDTO.builder()
                .title("A New Hope")
                .director("George Lucas")
                .build();

        when(filmService.getFilmByTitle(eq("A New Hope"))).thenReturn(film);

        mockMvc.perform(get("/api/v1/film/title/{title}", "A New Hope")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("A New Hope"))
                .andExpect(jsonPath("$.director").value("George Lucas"));
    }
}
