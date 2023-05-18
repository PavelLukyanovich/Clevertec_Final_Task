package ru.clevertec.userservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import ru.clevertec.userservice.util.Role;

import java.util.UUID;

import static ru.clevertec.userservice.util.StringConstant.LOGIN_PATTERN;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @Column(name = "id", columnDefinition = "uuid")
    @GeneratedValue
    private UUID id;

    @Column(name = "name")
    private String name;
    @Pattern(regexp = LOGIN_PATTERN)
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

}