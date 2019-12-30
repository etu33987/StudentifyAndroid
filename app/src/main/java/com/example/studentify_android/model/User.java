package com.example.studentify_android.model;

import com.example.studentify_android.utils.DateFormatUtil;

import java.util.Date;

public class User {
    private String id;
    private String eMail;
    private String name;
    private String firstname;
    private Date birthdate;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    private String picture;
    private Date endBanDate;
    private String sexe;
    private String banReason;
    private Address addressNavigation;
    private Date creationDate;

    public User() {
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getEndBanDate() {
        return endBanDate;
    }

    public void setEndBanDate(Date endBanDate) {
        this.endBanDate = endBanDate;
    }

    public String getBanReason() {
        return banReason;
    }

    public void setBanReason(String banReason) {
        this.banReason = banReason;
    }

    public Address getAddressNavigation() {
        return addressNavigation;
    }

    public void setAddressNavigation(Address addressNavigation) {
        this.addressNavigation = addressNavigation;
    }

    public String getBithdateFormated() {
        return DateFormatUtil.getStringForDate(this.birthdate);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", eMail='" + eMail + '\'' +
                ", name='" + name + '\'' +
                ", firstname='" + firstname + '\'' +
                ", birthdate=" + birthdate +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", picture='" + picture + '\'' +
                ", endBanDate=" + endBanDate +
                ", banReason='" + banReason + '\'' +
                ", addressNavigation=" + addressNavigation +
                '}';
    }
}
