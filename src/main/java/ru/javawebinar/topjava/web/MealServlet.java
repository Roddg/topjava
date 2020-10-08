package ru.javawebinar.topjava.web;


import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.dao.MealRepository;
import ru.javawebinar.topjava.dao.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.meals;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("GET request");
        String action = request.getParameter("action");

        if (action == null) {
            action = "getAll";
        }

        log.debug("Action: " + action);
        switch (action.toLowerCase()) {
            case "create": {
                Meal meal = new Meal(LocalDateTime.now(), "", 0);
                repository.save(meal);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/update.jsp").forward(request, response);
                break;
            }
            case "update": {
                long id = Long.parseLong(request.getParameter("id"));
                Meal meal = repository.get(id);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/update.jsp").forward(request, response);
                break;
            }
            case "delete": {
                long id = Long.parseLong(request.getParameter("id"));
                repository.delete(id);
                response.sendRedirect("meals");
                break;
            }
            case "getall": {
                List<MealTo> meals = MealsUtil.getMealToList(repository.getAll());
                request.setAttribute("meals", meals);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            }
            default: {
                request.setAttribute("meals", MealsUtil.getMealToList(meals));
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("POST request");
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        Meal newMeal = new Meal(
                id.isEmpty() ? null : Long.parseLong(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );

        repository.save(newMeal);
        response.sendRedirect("meals");
    }
}

