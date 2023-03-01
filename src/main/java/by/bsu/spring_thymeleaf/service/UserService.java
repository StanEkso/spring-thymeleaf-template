package by.bsu.spring_thymeleaf.service;

import by.bsu.spring_thymeleaf.entity.UserEntity;
import by.bsu.spring_thymeleaf.exception.UserAlreadyExistsException;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    private ArrayList<UserEntity> users = new ArrayList<>();

    public ArrayList<UserEntity> getUsers() {
        return users;
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return users.stream().filter((u) -> u.getEmail().equals(email)).findFirst();
    }

    public Optional<UserEntity> logIn(String email, String password) {
        return users
                .stream()
                .filter((u) -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst();
    }

    public UserEntity registerUser(UserEntity user) throws UserAlreadyExistsException {
        Optional<UserEntity> candidate = getUserByEmail(user.getEmail());
        if (candidate.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        this.users.add(user);

        return user;
    }
}
