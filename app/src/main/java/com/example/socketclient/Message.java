package com.example.socketclient;

import java.util.Date;

/**
 * Created by DPavliuk on 12.09.2017.
 */

public class Message {
    private String message;
    private Date date;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Message(String message, Date date) {
        this.message = message;
        this.date = date;

    }
}
