package com.example.trainix.dto;

import java.io.Serializable;
import java.time.Instant;

public class UserApiResponse implements Serializable {
    private int status;
    private String message;
    private Instant timeStamp;

    public UserApiResponse(int status, String message, long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = Instant.ofEpochSecond(timeStamp);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }
}
