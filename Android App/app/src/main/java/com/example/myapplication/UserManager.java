package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static List<User> users = new ArrayList<>();

    static {
        // Preload 10 users with proper names
        users.add(new User("john_doe", "John@123"));
        users.add(new User("emma_smith", "Emma@123"));
        users.add(new User("michael_jones", "Michael@123"));
        users.add(new User("sophia_wilson", "Sophia@123"));
        users.add(new User("william_brown", "William@123"));
        users.add(new User("ava_davis", "Ava@123"));
        users.add(new User("james_miller", "James@123"));
        users.add(new User("olivia_moore", "Olivia@123"));
        users.add(new User("benjamin_taylor", "Benjamin@123"));
        users.add(new User("mia_anderson", "Mia@123"));
    }

    public static boolean validateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static void registerUser(String username, String fullName) {
        String password = fullName + "@123"; // Auto-generate password in format "Name@123"
        users.add(new User(username, password));
    }
}
