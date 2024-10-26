package com.online.automobile.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "comapny")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EntityListeners(AuditingEntityListener.class)
public class Company implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "start_time")
    @Temporal(TemporalType.TIME)
    private Date startTime;

    @Column(name = "end_time")
    @Temporal(TemporalType.TIME)
    private Date endTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_user", referencedColumnName = "id")
    @JsonIgnore
    private User companyUser;

    @ManyToOne
    @JoinColumn(name = "company_type", referencedColumnName = "id")
    private CompanyType companyType;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "company_service_type"
            , joinColumns = {
            @JoinColumn(name = "company")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "service_type")
            }
    )
    private List<ServiceType> serviceTypes;

    @OneToMany(mappedBy = "companys")
    private List<PriceList>priceLists;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "last_modifiedby")
    private User lastModifiedby;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp_created")
    private Date timestampCreated;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp_modified")
    private Date timestampModified;

    @Column(name = "booking_limit")
    private int bookingLimit;

    @ManyToOne
    @JoinColumn(name = "company_Location", referencedColumnName = "id")
    private Location companyLocation;

    public Company() {
    }

    public Company(String companyName, String address, String phone, String email, String imgUrl, Date startTime, Date endTime, User companyUser, CompanyType companyType, User createdBy, User lastModifiedby, Date timestampCreated, Date timestampModified, Location companyLocation,int bookingLimit) {
        this.companyName = companyName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.imgUrl = imgUrl;
        this.startTime = startTime;
        this.endTime = endTime;
        this.companyUser = companyUser;
        this.companyType = companyType;
        this.createdBy = createdBy;
        this.lastModifiedby = lastModifiedby;
        this.timestampCreated = timestampCreated;
        this.timestampModified = timestampModified;
        this.companyLocation = companyLocation;
        this.bookingLimit = bookingLimit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public User getCompanyUser() {
        return companyUser;
    }

    public void setCompanyUser(User companyUser) {
        this.companyUser = companyUser;
    }

    public Location getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(Location companyLocation) {
        this.companyLocation = companyLocation;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getBookingLimit() {
        return bookingLimit;
    }

    public void setBookingLimit(int bookingLimit) {
        this.bookingLimit = bookingLimit;
    }

    public List<ServiceType> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<ServiceType> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public List<PriceList> getPriceLists() {
        return priceLists;
    }

    public void setPriceLists(List<PriceList> priceLists) {
        this.priceLists = priceLists;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                '}';
    }
}
