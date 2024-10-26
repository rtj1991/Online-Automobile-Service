package com.online.automobile.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "company_rating")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class CompanyRating implements Serializable {
    public static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "rating")
    private int rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company",referencedColumnName = "id")
    @JsonIgnore
    private Company company;
//
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ratingCompanyUser",referencedColumnName = "id")
    @JsonIgnore
    private User ratingCompanyUser;

    @Column(name = "status")
    private int status;

    public CompanyRating(int rating, Company company, User user, int status) {
        this.rating = rating;
        this.company = company;
        this.ratingCompanyUser = user;
        this.status = status;
    }

    public CompanyRating() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
//
    public User getUser() {
        return ratingCompanyUser;
    }

    public void setUser(User user) {
        this.ratingCompanyUser = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CompanyRating{" +
                "id=" + id +
                '}';
    }
}
