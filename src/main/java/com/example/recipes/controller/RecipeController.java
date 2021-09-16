package com.example.recipes.controller;

import com.example.recipes.exceptions.InvalidParamsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.recipes.exceptions.NoRequiredRightsException;
import com.example.recipes.exceptions.RecipeNotFoundException;
import com.example.recipes.model.Recipe;
import com.example.recipes.service.RecipeService;

import javax.validation.Valid;
import java.util.*;
import java.util.logging.Logger;

import static com.example.recipes.security.UserAuthentication.isCurrentUserOwner;

@RestController
@RequestMapping("/api/")
public class RecipeController {

    private final RecipeService recipeService;
    private static final Logger LOGGER = Logger.getLogger(RecipeController.class.getName());

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping(value = "recipe/new")
    public ResponseEntity<?> saveRecipe(@RequestBody @Valid Recipe recipe) {
        Recipe returnRecipe = recipeService.save(recipe);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("id", returnRecipe.getId()));
    }

    @GetMapping(value = "recipe/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable long id) {
        Recipe recipe = recipeService.findById(id).orElseThrow(RecipeNotFoundException::new);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recipe);
    }

    @DeleteMapping(value = "recipe/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable("id") long id) {
        Recipe recipe = recipeService.findById(id).orElseThrow(RecipeNotFoundException::new);

        if(isCurrentUserOwner(recipe.getOwner()))
        {
            recipeService.deleteById(id);
        }
        else {
            throw new NoRequiredRightsException();
        }
    }

    @PutMapping(value = "recipe/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRecipe(@PathVariable long id,
                             @RequestBody @Valid Recipe newRecipe) {

        Recipe oldRecipe = recipeService.findById(id).orElseThrow(RecipeNotFoundException::new);

        if (isCurrentUserOwner(oldRecipe.getOwner())) {
            recipeService.update(newRecipe,id);
        }
        else {
            throw new NoRequiredRightsException();
        }
    }

    @GetMapping(value = "recipe/search")
    public ResponseEntity<?> getRecipesByCategoryOrName(@RequestParam Map<String, String> queryParameters) {

        if (!recipeService.isSearchParamValid(queryParameters))
            throw new InvalidParamsException();

        List<Recipe> recipes = recipeService.searchAllRecipesByParameters(queryParameters);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recipes);
    }

}


