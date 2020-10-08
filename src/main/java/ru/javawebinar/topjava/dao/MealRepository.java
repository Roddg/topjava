package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {

    Meal save(Meal meal);

    void delete(long id);

    Meal get(long id);

    List<Meal> getAll();
}
