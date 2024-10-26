package com.online.automobile.service.vehicle;

import com.online.automobile.model.VehicleType;
import org.springframework.stereotype.Service;

import java.util.List;

public interface VehicleTypeService {
    void createVehicleTypes(List<VehicleType> vehicleTypes);

    List<VehicleType> getAllVehicleTypes();

}
