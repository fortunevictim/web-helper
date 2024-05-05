package ru.nsu.fit.pupynin.webhelper.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.nsu.fit.pupynin.webhelper.model.EmailRequest;
import ru.nsu.fit.pupynin.webhelper.service.EmailService;

@CrossOrigin
@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendErrorMsg")
    public ResponseEntity<?> sendErrorMsg(HttpSession session, @RequestBody EmailRequest emailRequest) {
        if (((String)session.getAttribute("email")) == null)
            return ResponseEntity.badRequest().body("Пользователь не авторизован");
        emailRequest.setFrom((String)session.getAttribute("email"));
        emailRequest.setTo("");
        emailRequest.setSubject("Сообщение об ошибке");
        System.out.println(emailRequest.getFrom());
        System.out.println(emailRequest.getTo());
        System.out.println(emailRequest.getSubject());
        System.out.println(emailRequest.getMessage());
        //emailService.sendErrmsgEmail(emailRequest.getFrom(), emailRequest.getSubject(), emailRequest.getMessage());
        return ResponseEntity.ok().body("Сообщение об ошибке было успешно отправлено!");
    }
}
