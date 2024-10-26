package com.online.automobile.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "vehicle_model")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class VehicleModel implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private int status;

    @OneToMany(mappedBy = "vehicle_model")
    private List<Booking> vehicleModels;

    public VehicleModel() {
    }

    public VehicleModel(String modelName, String description, int status) {
        this.modelName = modelName;
        this.description = description;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Booking> getVehicleModels() {
        return vehicleModels;
    }

    public void setVehicleModels(List<Booking> vehicleModels) {
        this.vehicleModels = vehicleModels;
    }

    @Override
    public String toString() {
        return "VehicleModel{" +
                "id=" + id +
                '}';
    }
}
