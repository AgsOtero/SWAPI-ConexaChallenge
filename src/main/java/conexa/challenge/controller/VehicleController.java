package conexa.challenge.controller;

import conexa.challenge.exception.ResourceNotFoundException;
import conexa.challenge.model.VehicleDTO;
import conexa.challenge.model.VehicleListDTO;
import conexa.challenge.services.VehicleService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/v1/vehicle")
@Api(value = "Vehicle Management System", tags = "Vehicles", description = "Operations pertaining to vehicles in Vehicle Management System")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @ApiOperation(value = "View a list of available vehicles", response = VehicleListDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public ResponseEntity<VehicleListDTO> getVehicles(
            @ApiParam(value = "Page number for pagination", defaultValue = "1")
            @RequestParam(value = "page", required = false, defaultValue = "1")
            @Min(value = 1, message = "Page number should not be less than 1") Integer page,
            @ApiParam(value = "Number of records per page", defaultValue = "10")
            @RequestParam(value = "limit", required = false, defaultValue = "10")
            @Min(value = 1, message = "Limit should not be less than 1") Integer limit) {
        return new ResponseEntity<>(vehicleService.getVehicles(page, limit), HttpStatus.OK);
    }

    @ApiOperation(value = "Get a vehicle by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved vehicle"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<VehicleDTO> getVehiclesById(
            @ApiParam(value = "ID of the vehicle to retrieve", required = true)
            @PathVariable("id")
            @Min(value = 1, message = "ID must be a positive integer") int id) {
        VehicleDTO vehicleDTO = vehicleService.getVehicleById(id);
        if (vehicleDTO == null) {
            throw new ResourceNotFoundException("Vehicle not found with ID: " + id);
        }
        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Get a vehicle by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved vehicle"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<VehicleDTO> getVehiclesByName(
            @ApiParam(value = "Name of the vehicle to retrieve", required = true)
            @PathVariable("name")
            @NotBlank(message = "Name cannot be blank") String name) {
        VehicleDTO vehicleDTO = vehicleService.getVehicleByName(name);
        if (vehicleDTO == null) {
            throw new ResourceNotFoundException("Vehicle not found with name: " + name);
        }
        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Get a vehicle by model")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved vehicle"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/model/{model}")
    public ResponseEntity<VehicleDTO> getVehiclesByModel(
            @ApiParam(value = "Model of the vehicle to retrieve", required = true)
            @PathVariable("model")
            @NotBlank(message = "Model cannot be blank") String model) {
        VehicleDTO vehicleDTO = vehicleService.getVehicleByModel(model);
        if (vehicleDTO == null) {
            throw new ResourceNotFoundException("Vehicle not found with model: " + model);
        }
        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }
}
