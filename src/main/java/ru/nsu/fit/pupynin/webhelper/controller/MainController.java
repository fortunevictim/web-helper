package ru.nsu.fit.pupynin.webhelper.controller;

import com.github.jsonldjava.utils.Obj;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Enumeration;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        model.addAttribute("isAuthenticated", session.getAttribute("email") != null);
        model.addAttribute("isAdmin", (session.getAttribute("role") == "admin" ));
        return "home";
    }

    //TODO переделать логику доступа админов к этой страницу, т.к. она для них бесполезна
    @GetMapping("/practice")
    public String practice(Model model, HttpSession session) {
        model.addAttribute("isAdmin", (session.getAttribute("role") == "admin" ));
        boolean isAuthenticated = session.getAttribute("email") != null;
        model.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated) {
            Boolean isBachelor = (Boolean) session.getAttribute("isBachelor");
            Boolean hasCoSupervisor = (Boolean) session.getAttribute("hasCoSupervisor");
            String studentProfile = (String) session.getAttribute("studentProfile");
            if (isBachelor == null || studentProfile == null) {
                model.addAttribute("errorMessage",
                        "У вас нет доступа к этой странице." +
                                " Пожалуйста, войдите в систему.");
                return "redirect:/login";
            }
            model.addAttribute("isBachelor", isBachelor);
            model.addAttribute("studentProfile", studentProfile);
            model.addAttribute("hasCoSupervisor", hasCoSupervisor);
        }
        return "practice";
    }

    @GetMapping("/diploma")
    public String diploma(Model model, HttpSession session) {
        boolean isAuthenticated = session.getAttribute("email") != null;
        model.addAttribute("isAdmin", (session.getAttribute("role") == "admin" ));
        model.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated) {
            Boolean isBachelor = (Boolean) session.getAttribute("isBachelor");
            Boolean hasCoSupervisor = (Boolean) session.getAttribute("hasCoSupervisor");
            String studentProfile = (String) session.getAttribute("studentProfile");
            if (isBachelor == null || studentProfile == null) {
                model.addAttribute("errorMessage",
                        "У вас нет доступа к этой странице." +
                                " Пожалуйста, войдите в систему.");
                return "redirect:/login";
            }
            model.addAttribute("isBachelor", isBachelor);
            model.addAttribute("studentProfile", studentProfile);
            System.out.println(session.getAttribute("studentProfile"));
            model.addAttribute("hasCoSupervisor", hasCoSupervisor);
        }
        return "diploma";
    }

    @GetMapping("/archive")
    public String test(Model model, HttpSession session) {
        model.addAttribute("isAuthenticated", session.getAttribute("email") != null);
        model.addAttribute("isAdmin", (session.getAttribute("role") == "admin" ));
        return "archive";
    }

    @GetMapping("/register")
    public String register(Model model, HttpSession session) {
        model.addAttribute("isAuthenticated", session.getAttribute("email") != null);
        model.addAttribute("isAdmin", (session.getAttribute("role") == "admin" ));
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model, HttpSession session) {
        model.addAttribute("isAuthenticated", session.getAttribute("email") != null);
        model.addAttribute("isAdmin", (session.getAttribute("role") == "admin" ));
        return "forgot-password";
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        model.addAttribute("isAuthenticated", session.getAttribute("email") != null);
        model.addAttribute("errorMessage", null);
        model.addAttribute("isAdmin", (session.getAttribute("role") == "admin" ));
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("email");
        return "redirect:/login";
    }

    @GetMapping("/control")
    public String control(Model model, HttpSession session) {
        model.addAttribute("isAuthenticated", session.getAttribute("email") != null);
        model.addAttribute("isAdmin", session.getAttribute("role").equals("admin"));

        String role = (String) session.getAttribute("role");
        if (role == null || role.equals("user")) {
            model.addAttribute("errorMessage",
                    "У вас нет доступа к этой странице." +
                            " Пожалуйста, войдите как администратор.");
            return "redirect:/login";
        }

        return "control";
    }

    @GetMapping("/errmsg")
    public String errmsg(Model model, HttpSession session) {
        model.addAttribute("isAuthenticated", session.getAttribute("email") != null);
        model.addAttribute("isAdmin", (session.getAttribute("role") == "admin" ));
//        Enumeration<String> attributeNames = session.getAttributeNames();
//        while (attributeNames.hasMoreElements()) {
//            String attributeName = attributeNames.nextElement();
//            Object attributeValue = session.getAttribute(attributeName);
//            System.out.println(attributeName + ": " + attributeValue);
//        }
        return "errmsg";
    }
}