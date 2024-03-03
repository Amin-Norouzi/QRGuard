package dev.aminnorouzi.qrguard.service;

import dev.aminnorouzi.qrguard.model.User;
import dev.aminnorouzi.qrguard.repository.UserRepository;
import dev.aminnorouzi.qrguard.util.DependencyInjector;

public class UserService {

    private final UserRepository repository = DependencyInjector.getInstance(UserRepository.class);

    public User save(String fullName, String email, String password) {
        User request = new User(fullName, email, password);
        return repository.save(request);
    }

    public User get(String email) {
        return repository.get(email);
    }

    public boolean authenticate(User user, String password) {
        return user.getPassword().equals(password);
    }
}
