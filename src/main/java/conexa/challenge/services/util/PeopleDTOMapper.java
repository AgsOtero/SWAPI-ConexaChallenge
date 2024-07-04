package conexa.challenge.services.util;

import conexa.challenge.model.PeopleDTO;

import java.util.Map;

public class PeopleDTOMapper {

    public static PeopleDTO mapPeopleDTO(Map<String, Object> properties) {
        return PeopleDTO.builder()
                .name((String) properties.get("name"))
                .height((String) properties.get("height"))
                .mass((String) properties.get("mass"))
                .hairColor((String) properties.get("hair_color"))
                .skinColor((String) properties.get("skin_color"))
                .eyeColor((String) properties.get("eye_color"))
                .birthYear((String) properties.get("birth_year"))
                .gender((String) properties.get("gender"))
                .homeworld(String.valueOf(properties.get("homeworld")))
                .url((String) properties.get("url"))
                .created((String) properties.get("created"))
                .edited((String) properties.get("edited"))
                .build();

    }

}
