package ru.skypro.homework.dto;

import lombok.*;

@Data
public class RegisterReqDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}
