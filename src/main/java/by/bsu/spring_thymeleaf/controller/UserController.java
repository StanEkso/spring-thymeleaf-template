package by.bsu.spring_thymeleaf.controller;

import by.bsu.spring_thymeleaf.entity.UserEntity;
import by.bsu.spring_thymeleaf.exception.UserServiceException;
import by.bsu.spring_thymeleaf.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequestMapping
@Controller
public class UserController {
    private final UserService userService;
    private static final Logger logger = LogManager.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getIndexPage(Model model) {
        logger.log(Level.INFO, "GET INDEX PAGE");
        return "index";
    }

    @GetMapping("signup")
    public String getSignupPage(Model model) {
        logger.log(Level.INFO, "GET SIGNUP PAGE");
        model.addAttribute("user", new UserEntity());
        return "signup";
    }

    @PostMapping("/signup")
    public String postUserEntity(@ModelAttribute("user") UserEntity user, Model model) {
        try {
            userService.registerUser(user);
        } catch (UserServiceException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/profile";
    }

    @GetMapping("signin")
    public String getSigninPage(Model model) {
        logger.log(Level.INFO, "GET SIGNUP PAGE");
        model.addAttribute("user", new UserEntity());
        return "signin";
    }

    @PostMapping("/signin")
    public String loginWithData(@ModelAttribute("user") UserEntity user, Model model) {
        Optional<UserEntity> candidate = userService.logIn(user.getEmail(), user.getPassword());
        if (candidate.isEmpty()) {
            return "redirect:/signin";
        }
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        model.addAttribute("users", userService.findUsers());
        return "profile";
    }
}
