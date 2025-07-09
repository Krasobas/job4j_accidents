package ru.job4j.accidents.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.dto.account.AccountCreateDto;
import ru.job4j.accidents.service.account.AccountService;

@Controller
@AllArgsConstructor
public class AccountController {
    private final AccountService service;

    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        return "security/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute AccountCreateDto user, Model model) {
        if (service.save(user).isEmpty()) {
            model.addAttribute("error", "User with this email already exists");
            return "/security/register";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Username or Password is incorrect !!");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been successfully logged out !!");
        }

        return "/security/login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }
}
