package com.online.automobile.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "vehicle_type")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class VehicleType implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private int status;

    @OneToMany(mappedBy = "vehicle_type")
    private List<Booking> vehicleTypes;

    public VehicleType() {
    }

    public VehicleType(String description, String name, int status) {
        this.description = description;
        this.name = name;
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Booking> getVehicleTypes() {
        return vehicleTypes;
    }

    public void setVehicleTypes(List<Booking> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

    @Override
    public String toString() {
        return "VehicleType{" +
                "id=" + id +
                '}';
    }
}
