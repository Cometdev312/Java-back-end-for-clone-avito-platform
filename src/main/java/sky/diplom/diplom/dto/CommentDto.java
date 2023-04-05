package sky.diplom.diplom.dto;

import lombok.Data;
import sky.diplom.diplom.entity.User;
import sky.diplom.diplom.entity.UserImage;

@Data
public class CommentDto {

    private int pk;
    private User author;
    private UserImage authorImage;
    private String authorFirstName;
    private int createdAt;
    private String text;

}
