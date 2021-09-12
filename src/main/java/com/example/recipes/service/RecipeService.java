package com.example.recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.recipes.model.Recipe;
import com.example.recipes.repository.RecipeRepository;

import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe save(Recipe toSave) {
        return recipeRepository.save(toSave);
    }

    public Optional<Recipe> findById(long id) {
        return recipeRepository.findById(id);
    }

    public boolean existsById(long id) {
        return recipeRepository.existsById(id);
    }

    public void deleteById(long id) {
        recipeRepository.deleteById(id);
    }
}
