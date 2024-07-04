package conexa.challenge.controller;

import conexa.challenge.model.FilmDTO;
import conexa.challenge.services.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/film")
public class FilmController {

    private final FilmService filmService;


    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<List<FilmDTO>> getFilms() {
        return new ResponseEntity<>(filmService.getAllFilms(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<FilmDTO> getFilmsById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(filmService.getFilmById(id), HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<FilmDTO> getFilmsByTitle(@PathVariable("title") String title) {
        return new ResponseEntity<>(filmService.getFilmByTitle(title), HttpStatus.OK);
    }

}
