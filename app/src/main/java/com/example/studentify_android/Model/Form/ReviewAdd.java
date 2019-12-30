package com.example.studentify_android.Model.Form;

import com.example.studentify_android.Model.Restaurant;
import com.example.studentify_android.Model.User;
import com.example.studentify_android.Utils.DateFormatUtil;

import java.util.Date;

public class ReviewAdd {

    private Integer id;
    private Integer note;
    private String description;
    private Date date;
    private Restaurant restaurantNavigation;
    private User userNavigation;

    public ReviewAdd(Integer id, Integer note, String description, Date date, Restaurant restaurantNavigation, User userNavigation) {
        this.id = id;
        this.note = note;
        this.description = description;
        this.date = date;
        this.restaurantNavigation = restaurantNavigation;
        this.userNavigation = userNavigation;
    }

    public ReviewAdd() {
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

    public Restaurant getrestaurantNavigation() {
        return restaurantNavigation;
    }

    public void setrestaurantNavigation(Restaurant restaurantNavigation) {
        this.restaurantNavigation = restaurantNavigation;
    }

    public User getuserNavigation() {
        return userNavigation;
    }

    public void setuserNavigation(User userNavigation) {
        this.userNavigation = userNavigation;
    }

    @Override
    public String toString() {
        return "ReviewAdd{" +
                "id=" + id +
                ", note=" + note +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", restaurantNavigation=" + restaurantNavigation +
                ", userNavigation=" + userNavigation +
                '}';
    }
}
