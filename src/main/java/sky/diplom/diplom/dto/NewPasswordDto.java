package sky.diplom.diplom.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class NewPasswordDto {
    private String currentPassword;
    private String newPassword;
}
