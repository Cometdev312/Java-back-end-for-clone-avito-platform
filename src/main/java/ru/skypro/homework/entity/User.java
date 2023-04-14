package ru.skypro.homework.entity;

import lombok.*;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "users")
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String city;
    private Instant regDate;

    @OneToOne()
    private Image image;

    @Enumerated(EnumType.STRING)
    private Role role;
}
