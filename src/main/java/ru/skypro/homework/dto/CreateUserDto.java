package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static ru.skypro.homework.constant.Regexp.EMAIL_REGEXP;
import static ru.skypro.homework.constant.Regexp.PHONE_REGEXP;

@Data
public class CreateUserDto {

    @Email(regexp = EMAIL_REGEXP)
    @Schema(example = "user@user.ru")
    private String email;
    @NotBlank
    @Size(min = 3)
    private String firstName;
    @NotBlank
    @Size(min = 3)
    private String lastName;
    @NotBlank
    @Size(min = 8)
    private String password;
    @Pattern(regexp = PHONE_REGEXP)
    private String phone;
}
