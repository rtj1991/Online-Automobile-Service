package com.online.automobile.dto;

import com.online.automobile.model.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BookingDetail extends AbstractSearchDetails{

    VehicleModel model;

    VehicleType vType;

    ServiceType serviceType;

    Booking booking;

    Company company;

    String vehicleNo;

    String description;

    Date appointedDate;

    String searchDate;

    public VehicleModel getModel() {
        return model;
    }

    public void setModel(VehicleModel model) {
        this.model = model;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAppointedDate() {
        return appointedDate;
    }

    public void setAppointedDate(Date appointedDate) {
        this.appointedDate = appointedDate;
    }

    public VehicleType getvType() {
        return vType;
    }

    public void setvType(VehicleType vType) {
        this.vType = vType;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }
}
