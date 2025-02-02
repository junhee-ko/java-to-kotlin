package me.jko.javatokotlin.controller;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String name;
    private String email;
}
