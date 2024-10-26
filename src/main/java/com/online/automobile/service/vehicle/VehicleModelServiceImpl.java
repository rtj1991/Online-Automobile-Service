package com.online.automobile.service.vehicle;

import com.online.automobile.model.VehicleModel;
import com.online.automobile.repository.VehicleModelRepository;
import com.online.automobile.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleModelServiceImpl implements VehicleModelService  {

    @Autowired
    private VehicleModelRepository modelRepository;

    @Override
    public void createVehicleTypes(List<VehicleModel> vehicleTypes) {
        modelRepository.save(vehicleTypes);
    }

    @Override
    public List<VehicleModel> getAllVehicleModels() {
        return modelRepository.findAllByStatus(Const.STATUS_ACTIVE);
    }
}
