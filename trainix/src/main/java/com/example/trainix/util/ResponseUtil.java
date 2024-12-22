package com.example.trainix.util;

import com.example.trainix.dto.ApiResponse;
import com.example.trainix.dto.UserApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>(data, HttpStatus.OK.value(), message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

        public static ResponseEntity<UserApiResponse> delete(String message, HttpStatus status) {
        UserApiResponse response = new UserApiResponse(HttpStatus.NO_CONTENT.value(), message, System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }


    public static <T> ResponseEntity<ApiResponse<T>> error(T data, String message, HttpStatus status) {
        ApiResponse<T> response = new ApiResponse<>(data, status.value(), message);
        return new ResponseEntity<>(response, status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> create(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>(data, HttpStatus.CREATED.value(), message);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public static ResponseEntity<UserApiResponse> okay(String message, HttpStatus status) {
        UserApiResponse response = new UserApiResponse(HttpStatus.CREATED.value(), message, System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    public static ResponseEntity<UserApiResponse> updated(String message, HttpStatus status) {
        UserApiResponse response = new UserApiResponse(HttpStatus.OK.value(), message, System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ApiResponse<T>> updatedWithData(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>(data, HttpStatus.OK.value(), message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
