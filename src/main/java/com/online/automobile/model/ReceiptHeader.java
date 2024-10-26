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
@Table(name = "receipt_header")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EntityListeners(AuditingEntityListener.class)
public class ReceiptHeader implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer Id;

    @OneToOne
    @JoinColumn(name = "booking")
    private Booking bookingId;

    @OneToOne
    @JoinColumn(name = "customer")
    private Customer customerId;

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

    @OneToMany(mappedBy = "booking")
    private List<BookingDetails> bookingDetails;

    @CreatedBy
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

    public ReceiptHeader() {
    }

    public ReceiptHeader(Booking bookingId, Customer customerId, String description, String vehicleNo, double totalAmount, double paidAmount, int paidStatus, List<BookingDetails> bookingDetails) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.description = description;
        this.vehicleNo = vehicleNo;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.paidStatus = paidStatus;
        this.bookingDetails = bookingDetails;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Booking getBookingId() {
        return bookingId;
    }

    public void setBookingId(Booking bookingId) {
        this.bookingId = bookingId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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

    public List<BookingDetails> getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(List<BookingDetails> bookingDetails) {
        this.bookingDetails = bookingDetails;
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

    @Override
    public String toString() {
        return "ReceiptHeader{" +
                "Id=" + Id +
                '}';
    }
}
