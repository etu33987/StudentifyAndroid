package com.example.studentify_android.model;

public class Address {
    private Integer id;
    private String streetName;
    private String city;
    private String streetNumber;
    private Integer zipCode;
    private String box;
    private Integer floor;

    public Address(Integer id, String streetName, String city, String streetNumber, Integer zipCode, String box, Integer floor) {
        this.id = id;
        this.streetName = streetName;
        this.city = city;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.box = box;
        this.floor = floor;
    }

    public Address(String streetName, String city, String streetNumber, Integer zipCode, String box, Integer floor) {
        this.streetName = streetName;
        this.city = city;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.box = box;
        this.floor = floor;
    }

    public Address() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", streetName='" + streetName + '\'' +
                ", city='" + city + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", zipCode=" + zipCode +
                ", box='" + box + '\'' +
                ", floor=" + floor +
                '}';
    }
}
