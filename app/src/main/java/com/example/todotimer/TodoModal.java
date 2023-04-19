package com.example.todotimer;

public class TodoModal {

    public String title, description;
    public int duration, time_left;
    public String id;

    public TodoModal() {
    }

    public TodoModal(String title, String description, int duration, int time_left, String id) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.time_left = time_left;
        this.id = id;
    }
}
