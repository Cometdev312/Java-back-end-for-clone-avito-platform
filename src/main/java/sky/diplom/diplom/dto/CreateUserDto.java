package sky.diplom.diplom.dto;

import lombok.Data;

@Data
public class CreateUserDto {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;

}
