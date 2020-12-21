package service;

import model.User;

public class UserCreator {

    public static final String USER_NAME="test.data.user.name";
    public static final String USER_PASSWORD="test.data.user.password";
    public static final String USER_EMAIL="test.data.user.email";

    public static User withAllProperty() {
        return new User(TestDataReader.getTestData(USER_NAME),
                TestDataReader.getTestData(USER_PASSWORD),
                TestDataReader.getTestData(USER_EMAIL));
    }

    public static User withEmptyEmail() {
        return new User(TestDataReader.getTestData(USER_NAME),
                TestDataReader.getTestData(USER_PASSWORD));
    }
}