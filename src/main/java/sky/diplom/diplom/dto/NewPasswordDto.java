package sky.diplom.diplom.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class NewPasswordDto {
    private String oldPassword;
    private String newPassword;
}
