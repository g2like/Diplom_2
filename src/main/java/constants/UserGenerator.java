package constants;
import com.github.javafaker.Faker;
import data.UserData;
import org.apache.commons.lang3.RandomStringUtils;

import java.awt.image.Kernel;
import java.util.Locale;

public class UserGenerator {
    /*public static String EMAIL_RANDOM;
    public static String PASSWORD_RANDOM;
    public static String NAME_RANDOM;

    public static UserData getRandomUser() {
        EMAIL_RANDOM = RandomStringUtils.randomAlphabetic(8) + "@yandex.ru";
        PASSWORD_RANDOM = RandomStringUtils.randomNumeric(8);
        NAME_RANDOM = RandomStringUtils.randomAlphabetic(8);
        return new UserData(EMAIL_RANDOM, PASSWORD_RANDOM, NAME_RANDOM);
    }*/

    static Faker faker = new Faker(new Locale("en"));

    public static String getEmail(){
        return faker.internet().emailAddress();
    }
    public static String getPassword(){
        return faker.internet().password();
    }
    public static String getName(){
        return faker.name().name();
    }

    public static UserData getRandomUser(){
        return new UserData(getEmail(),getPassword(),getName());
    }
}
