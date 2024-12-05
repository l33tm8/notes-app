package ru.ilya.notesapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.ilya.notesapp.entity.User;
import ru.ilya.notesapp.exception.UserAlreadyExistsException;
import ru.ilya.notesapp.service.UserService;

@Controller
public class RegistrationController {

    private final UserService userService;
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(User user, Model model) {
        try {
            userService.save(user);
            return "redirect:/login";
        }
        catch (UserAlreadyExistsException e) {
            model.addAttribute("message", e.getMessage());
            return "register";
        }
    }
}
