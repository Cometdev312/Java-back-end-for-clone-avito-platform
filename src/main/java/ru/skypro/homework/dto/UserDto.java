package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static ru.skypro.homework.constant.Regexp.EMAIL_REGEXP;
import static ru.skypro.homework.constant.Regexp.PHONE_REGEXP;

@Data
public class UserDto {

    private long id;
    @NotBlank
    @Size(min = 3)
    private String firstName;
    @NotBlank
    @Size(min = 3)
    private String lastName;
    @Email(regexp = EMAIL_REGEXP)
    @Schema(example = "user@user.ru")
    private String email;
    @Pattern(regexp = PHONE_REGEXP)
    private String phone;
    private String city;
    private String regDate;
    private String image;
}
