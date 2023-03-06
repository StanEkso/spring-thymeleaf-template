package by.bsu.spring_thymeleaf.service;

import by.bsu.spring_thymeleaf.entity.UserEntity;
import by.bsu.spring_thymeleaf.exception.UserServiceException;
import by.bsu.spring_thymeleaf.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> findUsers() {
        return this.userRepository.findAll();
    }

    public UserEntity findUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public UserEntity logIn(String email, String password) throws UserServiceException {
        UserEntity candidate = this.findUserByEmail(email);
        if (candidate == null) {
            throw new UserServiceException("User with this data never exist!");
        }
        if (Objects.equals(candidate.getPassword(), password)) {
            return candidate;
        }
        throw new UserServiceException("Entered data is incorrect!");
    }

    public UserEntity registerUser(UserEntity user) throws UserServiceException {
        UserEntity candidate = findUserByEmail(user.getEmail());
        if (candidate != null) {
            throw new UserServiceException("User with this email is already exists!");
        }
        return this.userRepository.save(user);
    }
}
