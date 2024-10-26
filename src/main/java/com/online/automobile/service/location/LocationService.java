package com.online.automobile.service.location;


import com.online.automobile.model.Location;

import java.util.List;

public interface LocationService {
    public List<Location> findAllLocation();

    void createLocation(List<Location> locations);
}
