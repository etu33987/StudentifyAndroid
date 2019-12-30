package com.example.studentify_android.Model;

public class Restaurant {

    private Integer id;
    private String name;
    private String type;
    private Integer address;
    private String menu;
    private String webSite;
    private String schedule;
    private String description;
    private String picture;
    private Address addressNavigation;

    public Restaurant(Integer id, String name, String type, Integer address, String menu, String webSite, String schedule, String description, String picture, Address addressNavigation) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.address = address;
        this.menu = menu;
        this.webSite = webSite;
        this.schedule = schedule;
        this.description = description;
        this.picture = picture;
        this.addressNavigation = addressNavigation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Address getAddressNavigation() {
        return addressNavigation;
    }

    public void setAddressNavigation(Address addressNavigation) {
        this.addressNavigation = addressNavigation;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", address=" + address +
                ", menu='" + menu + '\'' +
                ", webSite='" + webSite + '\'' +
                ", schedule='" + schedule + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                ", addressNavigation=" + addressNavigation +
                '}';
    }
}
