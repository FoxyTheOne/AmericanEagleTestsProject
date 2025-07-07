package utils;

import com.github.javafaker.Faker;

public class TestDataGeneratorUtils {
    private static final Faker faker = new Faker();

    public static String generateEmail() {
        return faker.internet().emailAddress();
    }

    public static String generatePassword() {
        return faker.internet().password(24, 25, true, true);
    }

    public static String generateFirstName() {
        return faker.name().firstName();
    }

    public static String generateLastName() {
//        return faker.name().lastName(); // Пишет набор символов
        return faker.name().firstName();
    }
}
