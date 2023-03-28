package sky.diplom.diplom.dto;

import lombok.Data;

@Data
public class CommentDto {

    private int id;
    private int author;
    private String authorImage;
    private String authorFirstName;
    private int createdAt;
    private String text;

}
