package conexa.challenge.controller;

import conexa.challenge.model.PeopleDTO;
import conexa.challenge.model.PeopleListDTO;
import conexa.challenge.services.PeopleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/people")
public class PeopleController {

    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public ResponseEntity<PeopleListDTO> getAllPeople(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                      @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return new ResponseEntity<>(peopleService.getPeople(page, size), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PeopleDTO> getPeopleById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(peopleService.getPeopleById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PeopleDTO> getPeopleByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(peopleService.getPeopleByName(name), HttpStatus.OK);
    }
}
