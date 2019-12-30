package com.example.studentify_android.model;

import java.util.Date;

public class Review {

    private Integer id;
    private Integer note;
    private String description;
    private Date date;
    private Restaurant restaurantNavigation;
    private User userNavigation;

    public Review(Integer id, Integer note, String description, Date date, Restaurant restaurantNavigation, User userNavigation) {
        this.id = id;
        this.note = note;
        this.description = description;
        this.date = date;
        this.restaurantNavigation = restaurantNavigation;
        this.userNavigation = userNavigation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Restaurant getRestaurantNavigation() {
        return restaurantNavigation;
    }

    public void setRestaurantNavigation(Restaurant restaurantNavigation) {
        this.restaurantNavigation = restaurantNavigation;
    }

    public User getUserNavigation() {
        return userNavigation;
    }

    public void setUserNavigation(User userNavigation) {
        this.userNavigation = userNavigation;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", note=" + note +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", restaurant=" + restaurantNavigation +
                ", user=" + userNavigation +
                '}';
    }
}
