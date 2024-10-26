package com.online.automobile.model;

import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class ApiBooking {
    private Integer id;
    private Date appointed_date;
    private int booking_type;
    private int cancel_status;
    private String description;
    private double paid_amount;
    private int paid_status;
    private int process_status;
    private Date timestamp_created;
    private Date timestamp_modified;
    private double total_amount;
    private String vehicle_no;
    private int booked_company;
    private int created_by;
    private int last_modifiedby;
    private int vehicle_model;
    private int vehicle_type;

    public ApiBooking() {
    }

    public ApiBooking(Integer id, Date appointed_date, int booking_type, int cancel_status, String description, double paid_amount, int paid_status, int process_status, Date timestamp_created, Date timestamp_modified, double total_amount, String vehicle_no, int booked_company, int created_by, int last_modifiedby, int vehicle_model, int vehicle_type) {
        this.id = id;
        this.appointed_date = appointed_date;
        this.booking_type = booking_type;
        this.cancel_status = cancel_status;
        this.description = description;
        this.paid_amount = paid_amount;
        this.paid_status = paid_status;
        this.process_status = process_status;
        this.timestamp_created = timestamp_created;
        this.timestamp_modified = timestamp_modified;
        this.total_amount = total_amount;
        this.vehicle_no = vehicle_no;
        this.booked_company = booked_company;
        this.created_by = created_by;
        this.last_modifiedby = last_modifiedby;
        this.vehicle_model = vehicle_model;
        this.vehicle_type = vehicle_type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAppointed_date() {
        return appointed_date;
    }

    public void setAppointed_date(Date appointed_date) {
        this.appointed_date = appointed_date;
    }

    public int getBooking_type() {
        return booking_type;
    }

    public void setBooking_type(int booking_type) {
        this.booking_type = booking_type;
    }

    public int getCancel_status() {
        return cancel_status;
    }

    public void setCancel_status(int cancel_status) {
        this.cancel_status = cancel_status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(double paid_amount) {
        this.paid_amount = paid_amount;
    }

    public int getPaid_status() {
        return paid_status;
    }

    public void setPaid_status(int paid_status) {
        this.paid_status = paid_status;
    }

    public int getProcess_status() {
        return process_status;
    }

    public void setProcess_status(int process_status) {
        this.process_status = process_status;
    }

    public Date getTimestamp_created() {
        return timestamp_created;
    }

    public void setTimestamp_created(Date timestamp_created) {
        this.timestamp_created = timestamp_created;
    }

    public Date getTimestamp_modified() {
        return timestamp_modified;
    }

    public void setTimestamp_modified(Date timestamp_modified) {
        this.timestamp_modified = timestamp_modified;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public int getBooked_company() {
        return booked_company;
    }

    public void setBooked_company(int booked_company) {
        this.booked_company = booked_company;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public int getLast_modifiedby() {
        return last_modifiedby;
    }

    public void setLast_modifiedby(int last_modifiedby) {
        this.last_modifiedby = last_modifiedby;
    }

    public int getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(int vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public int getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(int vehicle_type) {
        this.vehicle_type = vehicle_type;
    }
}
