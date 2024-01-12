package org.example.service;

import org.example.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public UserService() {
        users.put("bob", new User(123, "bob", "password123", false));
        users.put("alice", new User(321, "alice", "password321", false));
        users.put("admin", new User(777, "admin", "adminPassword", true));
    }

    public User getUserByPseudo(String pseudo) {
        return users.get(pseudo);
    }

    public boolean isConnectionValid(String pseudo, String password) {
        User user = users.get(pseudo);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }

    public User authenticate(String pseudo, String password) {
        User user = users.get(pseudo);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User getUserByUsername(String username) {
        return users.get(username);
    }
}
