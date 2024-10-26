package com.online.automobile.service.location;

import com.online.automobile.model.Location;
import com.online.automobile.repository.LocationRepository;
import com.online.automobile.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    LocationRepository locationRepository;

    @Override
    public List<Location> findAllLocation() {
        return locationRepository.findAllByStatus(Const.STATUS_ACTIVE);
    }

    @Override
    public void createLocation(List<Location> locations) {
        locationRepository.save(locations);
    }
}
