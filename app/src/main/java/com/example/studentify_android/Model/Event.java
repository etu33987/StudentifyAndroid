package com.example.studentify_android.Model;

import android.content.Intent;

import com.example.studentify_android.Utils.DateFormatUtil;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Event implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private String type;
    private Date beginDate;
    private Date endDate;
    private Address addressNavigation;
    private User authorNavigation;

    public Event(Integer id, String title, String description, String type, Date beginDate, Date endDate, Address addressNavigation, User authorNavigation) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.addressNavigation = addressNavigation;
        this.authorNavigation = authorNavigation;
    }

    public Event(String title, String description, String type, Date beginDate, Date endDate, Address addressNavigation, User authorNavigation) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.addressNavigation = addressNavigation;
        this.authorNavigation = authorNavigation;
    }

    public Event(String title, String description, String type, Date beginDate, Date endDate, Address addressNavigation) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.addressNavigation = addressNavigation;
    }

    public Event() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Address getAddressNavigation() {
        return addressNavigation;
    }

    public void setAddressNavigation(Address addressNavigation) {
        this.addressNavigation = addressNavigation;
    }

    public User getAuthorNavigation() {
        return authorNavigation;
    }

    public void setAuthorNavigation(User authorNavigation) {
        this.authorNavigation = authorNavigation;
    }

    public String getStartEventFormated() {
        return DateFormatUtil.getStringForDateTime(this.beginDate);
    }

    public String getEndEventFormated() {
        return DateFormatUtil.getStringForDateTime(this.endDate);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", addressNavigation=" + addressNavigation +
                ", authorNavigation=" + authorNavigation +
                '}';
    }
}