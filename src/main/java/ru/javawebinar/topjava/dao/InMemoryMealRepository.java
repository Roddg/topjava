package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
public class InMemoryMealRepository implements MealRepository {
    private Map<Long, Meal> repository = new ConcurrentHashMap<>();
    private static AtomicLong counter = new AtomicLong();

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);

        return meal;
    }

    @Override
    public void delete(long id) {
        repository.remove(id);
    }

    @Override
    public Meal get(long id) {
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(repository.values());
    }
}
