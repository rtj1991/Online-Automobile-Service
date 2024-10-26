package com.online.automobile.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class Information implements Serializable {
    @Size(min = 3, max = 100, message = "Full name must include more than 3 character")
    private String fullName;

    @Size(min = 3, max = 10, message = "Last name must include more than 3 character")
    private String lastName;

    @Pattern(flags = Pattern.Flag.CASE_INSENSITIVE, regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;

    //    @NotNull(message = "Select your business model")
    private Integer businessModel;

    private Integer userType;


    @Size(min = 3, max = 100, message = "Business name must include more than 3 character")
    private String companyName;
    private String location;


    @Size(min = 3, max = 100, message = "Reg. no must include more than 3 character")
    private String regNo;

//    @NotNull(message = "Required email")
    @Pattern(flags = Pattern.Flag.CASE_INSENSITIVE, regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String companyEmail;

    @Size(min = 3, max = 255, message = "Address must include more than 3 character")
    private String address;
    private String telephone;
    private String usermobile;
    private String fax;
    private String imageUrl;

    private File profileImage;
    private File companyLogo;
    private String nic;


//    @Size(min = 8, max = 255, message = "Password must include more than 8 character")
    private String password;

    private String startTime;
    private String endTime;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBusinessModel() {
        return businessModel;
    }

    public void setBusinessModel(Integer businessModel) {
        this.businessModel = businessModel;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }


    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public File getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(File profileImage) {
        this.profileImage = profileImage;
    }

    public File getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(File companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUsermobile() {
        return usermobile;
    }

    public void setUsermobile(String usermobile) {
        this.usermobile = usermobile;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
