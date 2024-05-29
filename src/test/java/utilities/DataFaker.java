package utilities;

import com.github.javafaker.Faker;

import java.util.Calendar;
import java.util.Date;

public class DataFaker {

    private final Faker faker;

    public DataFaker() {
        faker = new Faker();
    }

    public String getFakeFirstName() {
        return faker.name().firstName();
    }

    public String getFakeLastName() {
        return faker.name().lastName();
    }

    public String getFakeEmail() {
        return faker.internet().emailAddress();
    }

    public String getFakePassword() {
        return faker.internet().password();
    }

    public Date getFakeDateOfBirthday() {
        Date minimumDate = new Date(0, Calendar.FEBRUARY, 1); // year = 0 means 1900
        Date today = new Date(System.currentTimeMillis());
        return faker.date().between(minimumDate, today);
    }

    /* public String getFakeStreet() {
        return faker.address().streetName();
    }

    public String getFakeCity() {
        return faker.address().cityName();
    }

    public String getFakeMobilePhone() {
        return faker.phoneNumber().cellPhone();
    } */
}
