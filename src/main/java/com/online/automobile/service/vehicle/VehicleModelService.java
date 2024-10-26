package com.online.automobile.service.vehicle;

import com.online.automobile.model.VehicleModel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface VehicleModelService {
    void createVehicleTypes(List<VehicleModel> vehicleTypes);

    List<VehicleModel> getAllVehicleModels();
}
