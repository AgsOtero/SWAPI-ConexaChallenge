package conexa.challenge.controller;

import conexa.challenge.exception.ResourceNotFoundException;
import conexa.challenge.model.StarshipDTO;
import conexa.challenge.model.StarshipListDTO;
import conexa.challenge.services.StarshipService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/v1/starship")
@Api(value = "Starship Management System", tags = "Starships", description = "Operations pertaining to starships in Starship Management System")
public class StarshipController {

    private final StarshipService starshipService;

    public StarshipController(StarshipService starshipService) {
        this.starshipService = starshipService;
    }

    @ApiOperation(value = "View a list of available starships", response = StarshipListDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public ResponseEntity<StarshipListDTO> getStarships(
            @ApiParam(value = "Page number for pagination", defaultValue = "1")
            @RequestParam(value = "page", required = false, defaultValue = "1")
            @Min(value = 1, message = "Page number should not be less than 1") Integer page,
            @ApiParam(value = "Number of records per page", defaultValue = "10")
            @RequestParam(value = "limit", required = false, defaultValue = "10")
            @Min(value = 1, message = "Limit should not be less than 1") Integer limit) {
        return new ResponseEntity<>(starshipService.getStarships(page, limit), HttpStatus.OK);
    }

    @ApiOperation(value = "Get a starship by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved starship"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<StarshipDTO> getStarshipById(
            @ApiParam(value = "ID of the starship to retrieve", required = true)
            @PathVariable("id")
            @Min(value = 1, message = "ID must be a positive integer") int id) {
        StarshipDTO starshipDTO = starshipService.getStarshipById(id);
        if (starshipDTO == null) {
            throw new ResourceNotFoundException("Starship not found with ID: " + id);
        }
        return new ResponseEntity<>(starshipDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Get a starship by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved starship"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<StarshipDTO> getStarshipByName(
            @ApiParam(value = "Name of the starship to retrieve", required = true)
            @PathVariable("name")
            @NotBlank(message = "Name cannot be blank") String name) {
        StarshipDTO starshipDTO = starshipService.getStarshipByName(name);
        if (starshipDTO == null) {
            throw new ResourceNotFoundException("Starship not found with name: " + name);
        }
        return new ResponseEntity<>(starshipDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Get a starship by model")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved starship"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/model/{model}")
    public ResponseEntity<StarshipDTO> getStarshipByModel(
            @ApiParam(value = "Model of the starship to retrieve", required = true)
            @PathVariable("model")
            @NotBlank(message = "Model cannot be blank") String model) {
        StarshipDTO starshipDTO = starshipService.getStarshipByModel(model);
        if (starshipDTO == null) {
            throw new ResourceNotFoundException("Starship not found with model: " + model);
        }
        return new ResponseEntity<>(starshipDTO, HttpStatus.OK);
    }
}
