package com.online.automobile.dto;

import com.online.automobile.model.Location;
import org.springframework.stereotype.Component;

@Component
public class ReportSearch extends AbstractSearchDetails {
    Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
