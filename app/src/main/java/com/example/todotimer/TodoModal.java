package com.example.todotimer;

import java.io.Serializable;

public class TodoModal implements Serializable {

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
