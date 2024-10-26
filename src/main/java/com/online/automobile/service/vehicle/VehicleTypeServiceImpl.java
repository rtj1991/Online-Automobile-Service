package com.online.automobile.service.vehicle;

import com.online.automobile.model.VehicleType;
import com.online.automobile.repository.VehicleTypeRepository;
import com.online.automobile.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleTypeServiceImpl implements VehicleTypeService {

    @Autowired
    private VehicleTypeRepository typeRepository;

    @Override
    public void createVehicleTypes(List<VehicleType> vehicleTypes) {
        typeRepository.save(vehicleTypes);
    }

    @Override
    public List<VehicleType> getAllVehicleTypes() {
        return typeRepository.findAllByStatus(Const.STATUS_ACTIVE);
    }
}
