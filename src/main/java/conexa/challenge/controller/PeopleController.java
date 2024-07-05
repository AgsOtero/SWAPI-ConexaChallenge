package conexa.challenge.controller;

import conexa.challenge.exception.ResourceNotFoundException;
import conexa.challenge.model.PeopleDTO;
import conexa.challenge.model.PeopleListDTO;
import conexa.challenge.services.PeopleService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/v1/people")
@Api(value = "People Management System", tags = "People", description = "Operations pertaining to people in People Management System")
public class PeopleController {

    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @ApiOperation(value = "View a list of available people", response = PeopleListDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public ResponseEntity<PeopleListDTO> getAllPeople(
            @ApiParam(value = "Page number for pagination", defaultValue = "1")
            @RequestParam(value = "page", required = false, defaultValue = "1")
            @Min(value = 1, message = "Page number should not be less than 1") Integer page,
            @ApiParam(value = "Number of records per page", defaultValue = "10")
            @RequestParam(value = "size", required = false, defaultValue = "10")
            @Min(value = 1, message = "Size should not be less than 1") Integer limit) {
        return new ResponseEntity<>(peopleService.getPeople(page, limit), HttpStatus.OK);
    }

    @ApiOperation(value = "Get a person by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved person"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<PeopleDTO> getPeopleById(
            @ApiParam(value = "ID of the person to retrieve", required = true)
            @PathVariable("id")
            @Min(value = 1, message = "ID must be a positive integer") int id) {
        PeopleDTO peopleDTO = peopleService.getPeopleById(id);
        if (peopleDTO == null) {
            throw new ResourceNotFoundException("Person not found with ID: " + id);
        }
        return new ResponseEntity<>(peopleDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Get a person by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved person"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<PeopleDTO> getPeopleByName(
            @ApiParam(value = "Name of the person to retrieve", required = true)
            @PathVariable("name")
            @NotBlank(message = "Name cannot be blank") String name) {
        PeopleDTO peopleDTO = peopleService.getPeopleByName(name);
        if (peopleDTO == null) {
            throw new ResourceNotFoundException("Person not found with name: " + name);
        }
        return new ResponseEntity<>(peopleDTO, HttpStatus.OK);
    }
}
