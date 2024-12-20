package com.example.trainix.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> implements Serializable {
    private T data;
    private int status;
    private String message;
    private Instant timeStamp;

    public  ApiResponse(T data, int value, String message) {
        this.data =  data;
        this.status = value;
        this.message = message;
        this.timeStamp = Instant.now();
    }
}
