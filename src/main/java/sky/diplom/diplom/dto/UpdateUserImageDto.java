package sky.diplom.diplom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserImageDto {
    private Long id;
    private byte[] image;
    private Long fileSize;
    private String mediaType;
    private String title;
}
