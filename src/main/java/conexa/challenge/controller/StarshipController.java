package conexa.challenge.controller;

import conexa.challenge.model.StarshipDTO;
import conexa.challenge.model.StarshipListDTO;
import conexa.challenge.services.StarshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/starship")
public class StarshipController {

    private final StarshipService starshipService;

    public StarshipController(StarshipService starshipService) {
        this.starshipService = starshipService;
    }

    @GetMapping
    public ResponseEntity<StarshipListDTO> getStarships(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                        @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        return new ResponseEntity<>(starshipService.getStarships(page, limit), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<StarshipDTO> getStarshipById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(starshipService.getStarshipById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<StarshipDTO> getStarshipByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(starshipService.getStarshipByName(name), HttpStatus.OK);
    }

    @GetMapping("/model/{model}")
    public ResponseEntity<StarshipDTO> getStarshipByModel(@PathVariable("model") String model) {
        return new ResponseEntity<>(starshipService.getStarshipByModel(model), HttpStatus.OK);
    }

}
