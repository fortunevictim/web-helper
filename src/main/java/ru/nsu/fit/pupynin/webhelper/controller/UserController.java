package ru.nsu.fit.pupynin.webhelper.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.fit.pupynin.webhelper.model.UserLoginRequest;

@Controller
public class UserController {

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        model.addAttribute("isAuthenticated", session.getAttribute("email") != null);
        model.addAttribute("isAdmin", (session.getAttribute("role") == "admin" ));
        return "profile";
    }


}
