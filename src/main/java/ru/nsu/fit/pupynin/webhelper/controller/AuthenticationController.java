package ru.nsu.fit.pupynin.webhelper.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import ru.nsu.fit.pupynin.webhelper.model.UserChangePasswordRequest;
import ru.nsu.fit.pupynin.webhelper.model.UserLoginRequest;

import ru.nsu.fit.pupynin.webhelper.model.UserRegistrationRequest;
import ru.nsu.fit.pupynin.webhelper.service.EmailService;
import ru.nsu.fit.pupynin.webhelper.service.UserService;

import java.time.Year;
import java.util.Objects;

@CrossOrigin
@RestController
public class AuthenticationController {

    private final UserService userService;
    private final EmailService emailService;

    public AuthenticationController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        String email = request.getEmail();
        String role = userService.getUserRole(email); // Определяем роль пользователя
        if (!userService.isEmailValid(email)){
            return ResponseEntity.badRequest().body("Использованный email не может быть использован, так как не содержится в онтологии");
        }
        if (role == null) {
            return ResponseEntity.badRequest().body("Пользователь с таким email не найден.");
        }

        if (userService.isUserRegistered(email)) {
            return ResponseEntity.badRequest().body("Пользователь уже зарегистрирован. " +
                    "Если вы забыли пароль, то перейдите к восстановлению пароля.");
        }

        String password = userService.generatePassword();
        userService.updateUserPassword(email, password);
        String subject = "Регистрация nsuWebHelper";
        String text = "Вы успешно зарегистрировались на нашем сайте. Ваш пароль: " + password;
        //ОТПРАВКА ПОЧТА
        //emailService.sendEmail(email, subject, text);

        return ResponseEntity.ok().body("Регистрация прошла успешно. Пароль отправлен на вашу почту.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request, HttpSession session) {
        String email = request.getEmail();
        String password = request.getPassword();
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        // Возможно стоит уточнить по семестрам для корректного отображения кнопок по генерации в DocumentController
        boolean isUserValid = userService.isUserValid(email, password);
        if (Objects.equals(userService.getUserRole(email), "admin")) {
            session.setAttribute("email", email);
            session.setAttribute("role", userService.getUserRole(email));
            return ResponseEntity.ok().body("Вы успешно вошли в систему.");
        }
        else if (isUserValid) {
            boolean isBachelor = (Year.now().getValue() -
                    Integer.parseInt(userService.getUserGroup(email).substring(0,2)) > 2002);
            session.setAttribute("isBachelor", isBachelor);

            session.setAttribute("email", email);
            session.setAttribute("role", userService.getUserRole(email));
            //нужно для радела Диплом
            session.setAttribute("hasCoSupervisor", userService.getUserCoSupervisor(email) != null);
            String studentProfile = userService.getStudentProfile(email);
            //нужно для отображения корректной кнопки магистранту
            String profile = null;
            if (studentProfile.startsWith("Технология")) {
                profile = "ТРПС";
            }
            else if (studentProfile.startsWith("Компьютерное")) {
                profile = "КМиАД";
            }
            else profile = "ПИиКН";
            session.setAttribute("studentProfile", profile);

            System.out.println("studentProfile: " + profile +
                    ", email: " + email + ", role: " + session.getAttribute("role") +
                    ", hasCoSupervisor: " + session.getAttribute("hasCoSupervisor") +
                    ", isBachelor: " + session.getAttribute("isBachelor"));

            return ResponseEntity.ok().body("Вы успешно вошли в систему.");
        } else {
            return ResponseEntity.badRequest().body("Введенные логин и/или пароль оказались не верны.");
        }
    }
    @GetMapping("/isAuthenticated")
    public ResponseEntity<?> isAuthenticated(HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email != null) {
            return ResponseEntity.ok().body("Пользователь авторизован.");
        } else {
            return ResponseEntity.badRequest().body("Пользователь не авторизован.");
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody UserLoginRequest request) {
        String email = request.getEmail();
        String role = userService.getUserRole(email); // Определяем роль пользователя
        if (!userService.isEmailValid(email)){
            return ResponseEntity.badRequest().body("Использованный email не может быть использован, так как не содержится в онтологии");
        }
        if (role == null) {
            return ResponseEntity.badRequest().body("Пользователь с таким email не найден.");
        }

        if (!userService.isUserRegistered(email)) {
            return ResponseEntity.badRequest().body("Пользователь еще не зарегистрирован.");
        }

        String password = userService.generatePassword();
        userService.updateUserPassword(email, password);

        //ОТПРАВКА ПОЧТА
        String subject = "Смена пароля nsuWebHelper";
        String text = "Вы успешно сменили пароль. Ваш новый пароль: " + password;
        //emailService.sendEmail(email, subject, text);

        return ResponseEntity.ok().body("Смена пароля прошла успешно. Пароль отправлен на вашу почту.");
    }

    @PostMapping("/change-password")
    public ResponseEntity<?>  changePassword(@RequestBody UserChangePasswordRequest request, HttpSession session) {
        System.out.println("ALLLOLO" + request.getNewPassword() + " " + userService.getUserPassword((String) session.getAttribute("email")));
        if (Objects.equals(
                request.getNewPassword(),
                userService.getUserPassword((String) session.getAttribute("email"))))
            return ResponseEntity.badRequest().body("Новый пароль совпадает со старым");
        if (!Objects.equals(request.getNewPassword(), request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Пароли не совпадают");
        }
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return ResponseEntity.badRequest().body("Вы не авторизованы для смены пароля");
        }
        userService.updateUserPassword(email, request.getNewPassword());
        return ResponseEntity.ok().body("Пароль был успешно изменен.");
    }
}

