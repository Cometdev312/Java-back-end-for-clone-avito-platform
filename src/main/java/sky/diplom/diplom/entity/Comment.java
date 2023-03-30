package sky.diplom.diplom.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pk;
    private int author;
    private String authorImage;
    private String authorFirstName;
    private int createdAt;
    private String text;

}
