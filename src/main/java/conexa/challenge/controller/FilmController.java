package conexa.challenge.controller;

import conexa.challenge.exception.ResourceNotFoundException;
import conexa.challenge.model.FilmDTO;
import conexa.challenge.services.FilmService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/v1/film")
@Api(value = "Film Management System", tags = "Films", description = "Operations pertaining to films in Film Management System")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @ApiOperation(value = "View a list of available films", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public ResponseEntity<List<FilmDTO>> getFilms() {
        return new ResponseEntity<>(filmService.getAllFilms(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get a film by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved film"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<FilmDTO> getFilmsById(
            @ApiParam(value = "ID of the film to retrieve", required = true)
            @PathVariable("id")
            @NotNull(message = "ID cannot be null")
            @Min(value = 1, message = "ID must be a positive integer")
            Integer id) {
        FilmDTO filmDTO = filmService.getFilmById(id);
        if (filmDTO == null) {
            throw new ResourceNotFoundException("Film not found with ID: " + id);
        }
        return new ResponseEntity<>(filmDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Get a film by title")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved film"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/title/{title}")
    public ResponseEntity<FilmDTO> getFilmsByTitle(
            @ApiParam(value = "Title of the film to retrieve", required = true)
            @PathVariable("title")
            @NotBlank(message = "Title cannot be blank")
            String title) {
        FilmDTO filmDTO = filmService.getFilmByTitle(title);
        if (filmDTO == null) {
            throw new ResourceNotFoundException("Film not found with title: " + title);
        }
        return new ResponseEntity<>(filmDTO, HttpStatus.OK);
    }
}
