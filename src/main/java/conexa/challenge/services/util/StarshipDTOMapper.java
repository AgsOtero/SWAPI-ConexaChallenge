package conexa.challenge.services.util;

import conexa.challenge.model.StarshipDTO;

import java.util.List;
import java.util.Map;

public class StarshipDTOMapper {

    public static StarshipDTO mapStarshipDTO(Map<String, Object> properties){

        return StarshipDTO.builder()
                .crew((String) properties.get("crew"))
                .edited((String) properties.get("edited"))
                .created((String) properties.get("created"))
                .cargoCapacity(((String) properties.get("cargo_capacity")))
                .costInCredits(((String) properties.get("cost_in_credits")))
                .name(((String) properties.get("name")))
                .mglt(((String) properties.get("mglt")))
                .url(((String) properties.get("url")))
                .length(((String) properties.get("length")))
                .consumables(((String) properties.get("consumables")))
                .manufacturer(((String) properties.get("manufacturer")))
                .passengers(((String) properties.get("passengers")))
                .model(((String) properties.get("model")))
                .starshipClass(((String) properties.get("starship_class")))
                .hyperdriveRating(((String) properties.get("hyperdrive_rating")))
                .maxAtmospheringSpeed(((String) properties.get("max_atmosphering_speed")))
                .pilots((List<String>) properties.get("pilots"))
                .films((List<String>) properties.get("films"))
                .build();
    }
}
