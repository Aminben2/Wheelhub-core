package com.WheelHub.WheelHub.dto;

import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    private String name;
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
}