package conexa.challenge.controller;

import conexa.challenge.model.VehicleDTO;
import conexa.challenge.model.VehicleListDTO;
import conexa.challenge.services.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<VehicleListDTO> getVehicles(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                      @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        return new ResponseEntity<>(vehicleService.getVehicles(page, limit), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<VehicleDTO> getVehiclesById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(vehicleService.getVehicleById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<VehicleDTO> getVehiclesByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(vehicleService.getVehicleByName(name), HttpStatus.OK);
    }

    @GetMapping("/model/{model}")
    public ResponseEntity<VehicleDTO> getVehiclesByModel(@PathVariable("model") String model) {
        return new ResponseEntity<>(vehicleService.getVehicleByModel(model), HttpStatus.OK);
    }
}
