package com.online.automobile.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "booking")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@EntityListeners(AuditingEntityListener.class)
public class Booking implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer Id;

    @Column(name = "description")
    private String description;

    @Column(name = "vehicle_No")
    private String vehicleNo;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "paid_amount")
    private double paidAmount;

    @Column(name = "paid_status")
    private int paidStatus;

    @Column(name = "process_status")
    private int processStatus;

    @Column(name = "cancel_status")
    private int cancelStatus;

    @Column(name = "booking_type")
    private int bookingType;

    @OneToMany(mappedBy = "booking")
    private List<BookingDetails> bookingDetails;

    @ManyToOne
    @JoinColumn(name = "vehicle_model", referencedColumnName = "id")
    private VehicleModel vehicle_model;

    @ManyToOne
    @JoinColumn(name = "vehicle_type", referencedColumnName = "id")
    private VehicleType vehicle_type;

    @ManyToOne
    @JoinColumn(name = "booked_company", referencedColumnName = "id")
    private Company bookedCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lastModifiedby")
    @JsonIgnore
    private User lastModifiedby;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp_created")
    private Date timestampCreated;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp_modified")
    private Date timestampModified;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "appointed_date")
    private Date appointedDate;

    public Booking() {
    }

    public Booking(String description, String vehicleNo, VehicleModel vehicle_model, VehicleType vehicle_type, Company bookedCompany, ServiceType service_type, User createdBy, User lastModifiedby, Date timestampCreated, Date timestampModified, Date appointedDate) {
        this.description = description;
        this.vehicleNo = vehicleNo;
        this.vehicle_model = vehicle_model;
        this.vehicle_type = vehicle_type;
        this.bookedCompany = bookedCompany;
        this.createdBy = createdBy;
        this.lastModifiedby = lastModifiedby;
        this.timestampCreated = timestampCreated;
        this.timestampModified = timestampModified;
        this.appointedDate = appointedDate;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Company getBooked_company() {
        return bookedCompany;
    }

    public void setBooked_company(Company booked_company) {
        this.bookedCompany = booked_company;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getLastModifiedby() {
        return lastModifiedby;
    }

    public void setLastModifiedby(User lastModifiedby) {
        this.lastModifiedby = lastModifiedby;
    }

    public Date getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Date timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public Date getTimestampModified() {
        return timestampModified;
    }

    public void setTimestampModified(Date timestampModified) {
        this.timestampModified = timestampModified;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public VehicleModel getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(VehicleModel vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public VehicleType getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(VehicleType vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public List<BookingDetails> getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(List<BookingDetails> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    public Company getBookedCompany() {
        return bookedCompany;
    }

    public void setBookedCompany(Company bookedCompany) {
        this.bookedCompany = bookedCompany;
    }

    public Date getAppointedDate() {
        return appointedDate;
    }

    public void setAppointedDate(Date appointedDate) {
        this.appointedDate = appointedDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(int processStatus) {
        this.processStatus = processStatus;
    }

    public int getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(int cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public int getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(int paidStatus) {
        this.paidStatus = paidStatus;
    }

    public int getBookingType() {
        return bookingType;
    }

    public void setBookingType(int bookingType) {
        this.bookingType = bookingType;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "Id=" + Id +
                '}';
    }
}
