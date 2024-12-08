package org.poo.handlers;

import org.poo.bankInput.User;
import org.poo.fileio.UserInput;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static List<User> mapToUsers(final UserInput[] userInputs) {
        final List<User> users = new ArrayList<>();
        if (userInputs != null) {
            for (final UserInput userInput : userInputs) {
                users.add(new User(userInput.getFirstName(),
                        userInput.getLastName(),
                        userInput.getEmail()));
            }
        }
        return users;
    }
}
