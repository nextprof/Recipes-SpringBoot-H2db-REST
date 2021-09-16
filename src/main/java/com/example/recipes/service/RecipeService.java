package com.example.recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.recipes.model.Recipe;
import com.example.recipes.repository.RecipeRepository;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.recipes.security.UserAuthentication.getUsernameOfCurrentUser;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe save(Recipe toSave) {
        toSave.setDate(LocalDateTime.now());
        toSave.setOwner(getUsernameOfCurrentUser());
        return recipeRepository.save(toSave);
    }

    public void update(Recipe newRecipe,long id) {
        newRecipe.setId(id);
        newRecipe.setDate(LocalDateTime.now());
        newRecipe.setOwner(getUsernameOfCurrentUser());
        recipeRepository.save(newRecipe);
    }

    public Optional<Recipe> findById(long id) {
        return recipeRepository.findById(id);
    }

    public void deleteById(long id) {
        recipeRepository.deleteById(id);
    }

    public List<Recipe> searchAllRecipesByParameters(Map<String, String> queryParameters) {
        if (queryParameters.containsKey("name")) {
            return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(queryParameters.get("name"));
        }
        else if(queryParameters.containsKey("category")){
            return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(queryParameters.get("category"));
        }
        return Collections.emptyList();
    }

    public boolean isSearchParamValid(Map<String, String> queryParameters) {
        return queryParameters.size() == 1 &&
                SearchParameters.isParamValid(new ArrayList<>(queryParameters.keySet()).get(0));
    }

}
