package com.smart.entitits;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Contact
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "First name is required!")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters!")
    private String firstName;

    @NotBlank(message = "Last name is required!")
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters!")
    private String lastName;

    @NotBlank(message = "work is required!")
    @Size(max = 100, message = "Work/Profession cannot exceed 100 characters!")
    private String work;

    @NotBlank(message = "phone is required!")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits!")
    private String phone;

    @NotBlank(message = "email is required!")
    @Email(message = "Please provide a valid email!")
    private String email;

    private String image;

    @NotBlank(message = "About is required!")
    @Size(max = 100,message = "About cannot exceed 100 characters!")
    private String about;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
