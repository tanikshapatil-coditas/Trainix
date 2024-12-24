package com.example.trainix.entity;

import com.example.trainix.enums.Location;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.EnumNaming;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name",nullable = false)
    @NotBlank(message = "First Name should not be blank")
    @NotNull(message = "First name should not be null")
    private String firstName;

    @Column(name = "last_name",nullable = false)
    @NotBlank(message = "Last Name should not be blank")
    @NotNull(message = "Last name should not be null")
    private String lastName;

    @Column(name = "email", nullable = false)
    @Email(message = "Enter a valid email")
    @NotNull(message = "email should not be null")
    @UniqueElements(message = "Email should be unique")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "location")
    private Location location;

    @Column(name = "profile_pic")
    @NotEmpty(message = "Profile pic should not be empty")
    private String profilePic;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_tech_stack",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "tech_stack_id")
    )
    private Set<TechStack> techStackSet;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roleSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_course",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "courses_id")
    )
    private Set<Courses> coursesSet;
}
