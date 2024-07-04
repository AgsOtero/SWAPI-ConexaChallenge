package conexa.challenge.services;

import conexa.challenge.model.StarshipDTO;
import conexa.challenge.model.StarshipListDTO;

public interface StarshipService {

    StarshipListDTO getStarships(int page, int limit);

    StarshipDTO getStarshipById(int id);

    StarshipDTO getStarshipByName(String starshipName);

    StarshipDTO getStarshipByModel(String model);


}
