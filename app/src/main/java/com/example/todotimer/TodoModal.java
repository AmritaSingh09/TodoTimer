package com.example.todotimer;

import java.io.Serializable;

public class TodoModal {

    public String title, description, duration, status;

    public TodoModal(String title, String description, String duration, String status) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.status = status;
    }
}
