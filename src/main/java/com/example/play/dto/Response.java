package com.example.play.dto;


import lombok.Data;

@Data
public class Response<T> {
    private String message;
    private T data;

    public static <T> Response<T> success(T data, String message) {
        Response<T> res = new Response<>();
        res.data = data;
        res.message = message;
        return res;
    }
    public static <T> Response<T> error(T data, String message) {
        Response<T> res = new Response<>();
        res.data = data;
        res.message = message;
        return res;
    }
}