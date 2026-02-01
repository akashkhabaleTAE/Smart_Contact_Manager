package com.smart.entitits;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Name is required!")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Description is required!")
    @Size(min = 10, max = 500, message = "About must be between 10 and 500 characters")
    private String about;

    @NotBlank(message = "Username is required!")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private String imageUrl;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference
    @ToString.Exclude
    private List<Contact> contacts = new ArrayList<>();
}
