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
        assertMatch(service.get(MEAL_ID, USER_ID), meal11);
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
        ), meal31, meal21, meal11);
    }

    @Test
    public void getBetweenDatesNullDates() {
        assertMatch(service.getBetweenInclusive(null, null, USER_ID), USER_MEALS);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), meal71, meal61, meal51, meal41, meal31, meal21, meal11);
    }

    @Test
    public void update() throws Exception {
        service.update(getUpdated(), USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), getUpdated());
    }

    @Test
    public void updateNotFound() {
        assertThrows(NotFoundException.class, () -> service.update(meal11, USER_ID));
    }

    @Test
    public void updateWithIncorrectUser() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(), ANOTHER_USER_ID));
    }

    @Test
    public void create() throws Exception {
        Meal created = service.create(getNew(), USER_ID);
        getNew().setId(created.getId());
        assertMatch(getNew(), created);
        assertMatch(service.getAll(USER_ID), getNew(), meal71, meal61, meal51, meal41, meal31, meal21, meal11);
    }

        @Test
        public void duplicateDateCreate () {
            assertThrows(DataAccessException.class, () ->
                    service.create(new Meal(meal11.getDateTime(), "test", 500), USER_ID));
        }
    }
