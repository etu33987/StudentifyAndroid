package com.example.studentify_android.model;

public class Participation {

    private Integer id;
    private User userNavigation;
    private Event eventNavigation;

    public Participation(Integer id, User userNavigation, Event eventNavigation) {
        this.id = id;
        this.userNavigation = userNavigation;
        this.eventNavigation = eventNavigation;
    }

    public Participation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserNavigation() {
        return userNavigation;
    }

    public void setUserNavigation(User userNavigation) {
        this.userNavigation = userNavigation;
    }

    public Event getEventNavigation() {
        return eventNavigation;
    }

    public void setEventNavigation(Event eventNavigation) {
        this.eventNavigation = eventNavigation;
    }
}
