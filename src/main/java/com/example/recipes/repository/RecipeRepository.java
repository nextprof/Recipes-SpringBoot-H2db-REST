package com.example.recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.recipes.model.Recipe;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    Optional<Recipe> findById(long id);

    void deleteById(long id);

    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);

    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
