package conexa.challenge.services;

import conexa.challenge.model.PeopleDTO;
import conexa.challenge.model.PeopleListDTO;

public interface PeopleService {

    PeopleListDTO getPeople(Integer page, Integer limit);

    PeopleDTO getPeopleById(int id);

    PeopleDTO getPeopleByName(String name);
}
