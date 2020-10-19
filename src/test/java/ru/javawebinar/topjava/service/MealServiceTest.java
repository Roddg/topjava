package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-jdbc.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        assertMatch(service.get(MEAL_ID, USER_ID), MEAL11);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getWithIncorrectUser() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, ANOTHER_USER_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deletedWithIncorrectUser() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID, ANOTHER_USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(
                LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30),
                USER_ID
        ), MEAL31, MEAL21, MEAL11);
    }

    @Test
    public void getBetweenDatesNullDates() {
        assertMatch(service.getBetweenInclusive(null, null, USER_ID), USER_MEALS);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), MEAL71, MEAL61, MEAL51, MEAL41, MEAL31, MEAL21, MEAL11);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), updated);
    }

    @Test
    public void updateNotFound() {
        assertThrows(NotFoundException.class, () -> service.update(MEAL11, USER_ID));
    }

    @Test
    public void updateWithIncorrectUser() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(), ANOTHER_USER_ID));
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(newMeal, created);
        assertMatch(service.getAll(USER_ID), newMeal, MEAL71, MEAL61, MEAL51, MEAL41, MEAL31, MEAL21, MEAL11);
    }

        @Test
        public void duplicateDateCreate () {
            assertThrows(DataAccessException.class, () ->
                    service.create(new Meal(MEAL11.getDateTime(), "test", 500), USER_ID));
        }
    }
