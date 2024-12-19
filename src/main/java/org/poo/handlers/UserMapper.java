package org.poo.handlers;

import org.poo.bankInput.User;
import org.poo.fileio.UserInput;

import java.util.ArrayList;
import java.util.List;

/**
 * The type User mapper.
 */
public final class UserMapper {
    /**
     * Private constructor to prevent instantiation.
     */
    private UserMapper() {
        throw new UnsupportedOperationException("Utility class");
    }
    /**
     * Map to users list.
     *
     * @param userInputs the user inputs
     * @return the list
     */
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
