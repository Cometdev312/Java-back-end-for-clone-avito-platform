package sky.diplom.diplom.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User author;
    @OneToOne
    private UserImage authorImage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_name")
    private User authorFirstName;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "text")
    private String text;

}
