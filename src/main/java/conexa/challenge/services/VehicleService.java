package conexa.challenge.services;

import conexa.challenge.model.VehicleDTO;
import conexa.challenge.model.VehicleListDTO;

public interface VehicleService {

    VehicleListDTO getVehicles(int page, int limit);

    VehicleDTO getVehicleById(int id);

    VehicleDTO getVehicleByName(String name);

    VehicleDTO getVehicleByModel(String model);

}
