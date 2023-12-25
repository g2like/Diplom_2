package order;

import com.github.javafaker.Faker;
import data.UserData;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderGenerator {
    /*public static List<String> INGREDIENT;

    public static OrderData getIngredient() {
        INGREDIENT = List.of("61c0c5a71d1f82001bdaaa6f");

        return new OrderData(INGREDIENT);
    }

    public static OrderData getInvalidIngredient() {
        INGREDIENT = List.of("61c0c5a71d");

        return new OrderData(INGREDIENT);
    }*/

    static Faker faker = new Faker();

    private static int randomIndex;
    private static ArrayList<String> availableIds;
    private static ArrayList<String> orderIds;

    public static ArrayList<String> getCorrectOrderIds(ArrayList<String> availableIds, int amount) {
        orderIds = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            randomIndex = faker.number().numberBetween(0, availableIds.size() - 1);
            orderIds.add(availableIds.get(randomIndex));
        }
        return orderIds;
    }

    public static ArrayList<String> getWrongOrderIds(ArrayList<String> availableIds, int amount) {
        orderIds = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            orderIds.add(RandomStringUtils.random(24));
        }
        return orderIds;
    }


}
