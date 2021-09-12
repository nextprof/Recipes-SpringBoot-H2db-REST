package com.example.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @JsonIgnore
    private long id;

    @NotBlank(message = "recipe name must not be blank")
    private String name;

    @NotBlank(message = "recipe description must not be blank")
    private String description;

    @ElementCollection
    @NotEmpty(message = "recipe must have at least 1 ingredient")
    private List<String> ingredients = new ArrayList<>();

    @ElementCollection
    @NotEmpty(message = "recipe must have at least 1 directions")
    private List<String> directions = new ArrayList<>();

}
