package com.example.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @JsonIgnore
    private Long id;

    @Pattern(regexp = "^(.+)@(.+)[.](.+)$")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min=8, message= "Password should has at least 8 characters")
    private String password;

}
