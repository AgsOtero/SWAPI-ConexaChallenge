package conexa.challenge.services.util;

import conexa.challenge.model.VehicleDTO;

import java.util.List;
import java.util.Map;

public class VehicleDTOMapper {

    public static VehicleDTO mapVehicleDTO(Map<String, Object> properties) {

        return VehicleDTO.builder()
                .crew((String) properties.get("crew"))
                .edited((String) properties.get("edited"))
                .created((String) properties.get("created"))
                .cargoCapacity(((String) properties.get("cargo_capacity")))
                .costInCredits(((String) properties.get("cost_in_credits")))
                .name(((String) properties.get("name")))
                .url(((String) properties.get("url")))
                .length(((String) properties.get("length")))
                .consumables(((String) properties.get("consumables")))
                .manufacturer(((String) properties.get("manufacturer")))
                .passengers(((String) properties.get("passengers")))
                .model(((String) properties.get("model")))
                .maxAtmospheringSpeed(((String) properties.get("max_atmosphering_speed")))
                .pilots((List<String>) properties.get("pilots"))
                .films((List<String>) properties.get("films"))
                .vehicleClass(((String) properties.get("vehicle_class")))
                .build();
    }


}
