package com.online.automobile.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "service_type")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ServiceType implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "service_type")
    private String serviceType;

    @Column(name = "status")
    private int status;

    @Column(name = "type")
    private int type;

    @OneToMany(mappedBy = "serviceType")
    private List<BookingDetails> service_type;

    @OneToMany(mappedBy = "serviceType")
    private List<PriceList> priceLists;

    @ManyToMany(mappedBy = "serviceTypes")
    @JsonIgnore
    private List<Company> companies;

    public ServiceType() {
    }

    public ServiceType(String description, String serviceType, int status, int type) {
        this.description = description;
        this.serviceType = serviceType;
        this.status = status;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<BookingDetails> getService_type() {
        return service_type;
    }

    public void setService_type(List<BookingDetails> service_type) {
        this.service_type = service_type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<PriceList> getPriceLists() {
        return priceLists;
    }

    public void setPriceLists(List<PriceList> priceLists) {
        this.priceLists = priceLists;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    @Override
    public String toString() {
        return "ServiceType{" +
                "id=" + id +
                '}';
    }
}

