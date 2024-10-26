package com.online.automobile.dto;

import com.online.automobile.model.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public abstract class AbstractSearchDetails {
    public Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date dateFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date dateTo;

    public Integer status;
    public User createdBy;
    public User modifiedBy;
    public User authorizedBy;
    public Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public User getAuthorizedBy() {
        return authorizedBy;
    }

    public void setAuthorizedBy(User authorizedBy) {
        this.authorizedBy = authorizedBy;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
