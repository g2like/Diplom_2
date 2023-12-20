package constants;

import data.UserData;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomDataUser {
    public static String EMAIL_RANDOM;
    public static String PASSWORD_RANDOM;
    public static String NAME_RANDOM;

    public static UserData getRandomUser() {
        EMAIL_RANDOM = RandomStringUtils.randomAlphabetic(8) + "@yandex.ru";
        PASSWORD_RANDOM = RandomStringUtils.randomNumeric(8);
        NAME_RANDOM = RandomStringUtils.randomAlphabetic(8);

        return new UserData(EMAIL_RANDOM, PASSWORD_RANDOM, NAME_RANDOM);
    }
}
