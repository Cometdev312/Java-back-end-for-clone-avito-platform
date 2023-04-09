package sky.diplom.diplom.dto;

import lombok.Data;

@Data
public class RegisterReqDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}