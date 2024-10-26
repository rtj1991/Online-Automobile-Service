package com.online.automobile.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "userName")
    private String userName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "locked")
    private boolean locked;

    @Column(name = "nic")
    private String nic;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "recovery_email")
    private String recoveryEmail;

    @Column(name = "recovery_phone")
    private String recoveryPhone;

    @Column(name = "logged_in")
    private boolean loggedIn;

    @Column(name = "menu_collapse")
    private boolean menuCollapse;

    @Column(name = "ip_address", nullable = true)
    private String ipAddress;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp_created")
    private Date timestampCreated;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "user_type")
    private int userType;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role"
            , joinColumns = {
            @JoinColumn(name = "user")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "role")
            }
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "createdBy")
    @JsonBackReference
    private List<Company> comCreatedBy;

    @OneToMany(mappedBy = "lastModifiedby")
    @JsonBackReference
    private List<Company> companyLastModifiedby;

    @OneToMany(mappedBy = "createdBy")
    @JsonBackReference
    private List<Booking> bookingCreatedBy;

    @OneToMany(mappedBy = "lastModifiedby")
    @JsonBackReference
    private List<Booking> bookingLastModifiedby;

    @OneToMany(mappedBy = "createdBy")
    @JsonBackReference
    private List<ReceiptHeader> receiptCreatedBy;

    @OneToMany(mappedBy = "lastModifiedby")
    @JsonBackReference
    private List<ReceiptHeader> receiptLastModifiedby;

    //bi-directional many-to-one association to Location
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location", referencedColumnName = "id")
    @JsonIgnore
    private Location location;

    @OneToMany(mappedBy = "ratingCompanyUser")
    @JsonBackReference
    private List<CompanyRating> ratingCompanyUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRecoveryEmail() {
        return recoveryEmail;
    }

    public void setRecoveryEmail(String recoveryEmail) {
        this.recoveryEmail = recoveryEmail;
    }

    public String getRecoveryPhone() {
        return recoveryPhone;
    }

    public void setRecoveryPhone(String recoveryPhone) {
        this.recoveryPhone = recoveryPhone;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isMenuCollapse() {
        return menuCollapse;
    }

    public void setMenuCollapse(boolean menuCollapse) {
        this.menuCollapse = menuCollapse;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Company> getComCreatedBy() {
        return comCreatedBy;
    }

    public void setComCreatedBy(List<Company> comCreatedBy) {
        this.comCreatedBy = comCreatedBy;
    }

    public List<Company> getCompanyLastModifiedby() {
        return companyLastModifiedby;
    }

    public void setCompanyLastModifiedby(List<Company> companyLastModifiedby) {
        this.companyLastModifiedby = companyLastModifiedby;
    }
    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public List<Booking> getBookingCreatedBy() {
        return bookingCreatedBy;
    }

    public void setBookingCreatedBy(List<Booking> bookingCreatedBy) {
        this.bookingCreatedBy = bookingCreatedBy;
    }

    public List<Booking> getBookingLastModifiedby() {
        return bookingLastModifiedby;
    }

    public void setBookingLastModifiedby(List<Booking> bookingLastModifiedby) {
        this.bookingLastModifiedby = bookingLastModifiedby;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Date timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public User() {
    }

    public List<ReceiptHeader> getReceiptCreatedBy() {
        return receiptCreatedBy;
    }

    public void setReceiptCreatedBy(List<ReceiptHeader> receiptCreatedBy) {
        this.receiptCreatedBy = receiptCreatedBy;
    }

    public List<ReceiptHeader> getReceiptLastModifiedby() {
        return receiptLastModifiedby;
    }

    public void setReceiptLastModifiedby(List<ReceiptHeader> receiptLastModifiedby) {
        this.receiptLastModifiedby = receiptLastModifiedby;
    }

    public List<CompanyRating> getRatingCompanyUser() {
        return ratingCompanyUser;
    }

    public void setRatingCompanyUser(List<CompanyRating> ratingCompanyUser) {
        this.ratingCompanyUser = ratingCompanyUser;
    }

    @Override
    public String toString() {
        return "user{" +
                "id" + id +
                '}';
    }
}
