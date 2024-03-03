package dev.aminnorouzi.qrguard.repository;

import dev.aminnorouzi.qrguard.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private final Map<String, User> users = new HashMap<>();

    public User save(User user) {
        if (users.containsKey(user.getEmail())) {
            throw new RuntimeException("User already exists.");
        }

        users.put(user.getEmail(), user);
        return user;
    }

    public User get(String email) {
        if (!users.containsKey(email)) {
            throw new RuntimeException("User does not exist.");
        }

        return users.get(email);
    }
}
