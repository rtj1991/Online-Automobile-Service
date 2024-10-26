package com.online.automobile.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "price_list")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PriceList implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "prl")
    private String prl;

    @Column(name = "amount")
    private double amount;

    @ManyToOne
    @JoinColumn(name = "service_type",referencedColumnName = "id")
    private ServiceType serviceType;

    @ManyToOne
    @JoinColumn(name = "company_lists",referencedColumnName = "id")
    private Company companys;

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

    @Column(name = "status")
    private int status;

    public PriceList() {
    }

    public PriceList(String prl, double amount, ServiceType serviceType, User createdBy, User lastModifiedby, Date timestampCreated, Date timestampModified,int status) {
        this.prl = prl;
        this.amount = amount;
        this.serviceType = serviceType;
        this.createdBy = createdBy;
        this.lastModifiedby = lastModifiedby;
        this.timestampCreated = timestampCreated;
        this.timestampModified = timestampModified;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrl() {
        return prl;
    }

    public void setPrl(String prl) {
        this.prl = prl;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
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

    public Company getCompanys() {
        return companys;
    }

    public void setCompanys(Company companys) {
        this.companys = companys;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PriceList{" +
                "id=" + id +
                '}';
    }
}
