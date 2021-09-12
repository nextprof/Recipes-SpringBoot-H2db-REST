package com.example.recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.recipes.exceptions.RecipeNotFoundException;
import com.example.recipes.model.Recipe;
import com.example.recipes.service.RecipeService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe/")
@Validated
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping(value = "new")
    public ResponseEntity<?> saveRecipe(@RequestBody @Valid Recipe recipe) {
        Recipe returnRecipe = recipeService.save(recipe);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("id",returnRecipe.getId()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> getRecipe(@PathVariable long id) {
        Recipe recipe = recipeService.findById(id).orElseThrow(RecipeNotFoundException::new);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(recipe);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable long id) {

        if(recipeService.existsById(id)) {
            recipeService.deleteById(id);
        }
        else {
            throw new RecipeNotFoundException();
        }

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
