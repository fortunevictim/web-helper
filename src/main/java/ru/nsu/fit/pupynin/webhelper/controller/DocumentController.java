package ru.nsu.fit.pupynin.webhelper.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.fit.pupynin.webhelper.JenaWork.Helper;
import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.DocumentTemplateFilter;
import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.core.*;
import ru.nsu.fit.pupynin.webhelper.service.UserService;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import static ru.nsu.fit.pupynin.webhelper.JenaWork.Helper.generateTemplates;

@Controller
@RequestMapping("/documents")
public class DocumentController {
    private final UserService userService;

    public DocumentController(UserService userService) {
        this.userService = userService;
    }

    //TODO need to change it to work with other courses
    private String generateFileName(String nameTemplate, String reportType, String email) {
        String fileNameTemplate = nameTemplate;
        String FIO = userService.getUserFIO(email).replace(" ", "_");
        String latin;
        if (reportType.equals("Магистратура_ТРПС"))
            latin = "TRPS";
        else if (reportType.equals("Магистратура_КМиАД"))
            latin = "KMiAD";
        else latin = "PIiKN";
        return fileNameTemplate
                .replace("{Ф_И_О}", FIO)
                .replace("{Тип_программы}", reportType)
                .replace("{Сем}",
                        (reportType.startsWith("Магистратура") ? "4 сем":"8 семестр"))
                .replace("{Направление}",
                        (reportType.startsWith("Магистратура") ? "09.04.01":"09.03.01"))
                .replace("{Тип_программы_латиницей}", latin );
    }

    //TODO need to change it to work with other courses
    private String generateFilePath(String reportType, String fileName) {
        String filePath = "target/classes/documents" +
                (reportType.startsWith("Магистратура") ?
                        "/masters/2nd_course" : "/bachelors/4th_course/"
                );
        if (reportType.startsWith("Магистратура"))
            filePath += (reportType.equals("Магистратура_ТРПС") ? "/tprs/" : "/mda/");
        filePath += fileName;
        System.out.println(filePath);
        return filePath;
    }

    private ResponseEntity<?> generateReport(String nameTemplate, String reportType, String email,
                                             DocumentTemplateFilter documentTemplateFilter) throws IOException {
        Helper.generateTemplates(documentTemplateFilter, email);
        String generatedFileName = generateFileName(nameTemplate, reportType, email);
        System.out.println(generatedFileName);
        String filePath = generateFilePath(reportType, generatedFileName);
        System.out.println("filepath is: " + filePath);

        File file = new File(filePath);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Файл не найден.");
        }

        HttpHeaders headers = new HttpHeaders();
        String encodedFileName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8.toString());
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new FileSystemResource(file));
    }


    //Маппинг для раздела Практика
    @PostMapping("/generate_practice_report")
    public ResponseEntity<?> generatePracticeReport(HttpSession session, @RequestParam String reportType) throws IOException {
        String email = (String) session.getAttribute("email");
        String nameTemplate = "{Ф_И_О}_Прил 2_Отчет о практике_{Тип_программы}_{Сем}.docx";
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Пользователь не авторизован.");
        }
        DocumentTemplateFilter documentTemplateFilter = null;
        if (session.getAttribute("isBachelor").equals(true))
            documentTemplateFilter = template -> template instanceof PracticeReport;
        else if (session.getAttribute("studentProfile") == "КМиАД")
            documentTemplateFilter = template -> template instanceof PracticeReportMDA;
        else documentTemplateFilter = template -> template instanceof PracticeReportTRPS;
        return generateReport(nameTemplate, reportType, email, documentTemplateFilter);
    }

    @PostMapping("/generate_individual_task_report")
    public ResponseEntity<?> generateIndividualTaskReport(HttpSession session, @RequestParam String reportType) throws IOException {
        String email = (String) session.getAttribute("email");
        String nameTemplate = "{Ф_И_О}_Прил 1_ИЗ на практику_{Тип_программы}_{Сем}.docx";
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Пользователь не авторизован.");
        }
        DocumentTemplateFilter documentTemplateFilter = null;
        if (session.getAttribute("isBachelor").equals(true))
            documentTemplateFilter = template -> template instanceof IndividualTask;
        else if (session.getAttribute("studentProfile") == "КМиАД")
            documentTemplateFilter = template -> template instanceof IndividualTaskMDA;
        else documentTemplateFilter = template -> template instanceof IndividualTaskTRPS;
        return generateReport(nameTemplate, reportType, email, documentTemplateFilter);
    }

    @PostMapping("/generate_practice_feedback_report")
    public ResponseEntity<?> generatePracticeFeedbackReport(HttpSession session, @RequestParam String reportType) throws IOException {
        String email = (String) session.getAttribute("email");
        String nameTemplate = null;
        System.out.println("1");
        if (reportType.startsWith("Бакалавриат"))
            nameTemplate = "{Ф_И_О}_Прил 3_Отзыв руководителя практики_{Тип_программы}_{Сем}.docx";
        else
            nameTemplate = "{Ф_И_О}_Прил 3_Отзыв руководителя_{Тип_программы}_{Сем}.docx";
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Пользователь не авторизован.");
        }
        DocumentTemplateFilter documentTemplateFilter = null;
        if (session.getAttribute("isBachelor").equals(true))
            documentTemplateFilter = template -> template instanceof PracticeFeedback;
        else if (session.getAttribute("studentProfile") == "КМиАД")
            documentTemplateFilter = template -> template instanceof PracticeFeedbackMDA;
        else documentTemplateFilter = template -> template instanceof PracticeFeedbackTRPS;
        return generateReport(nameTemplate, reportType, email, documentTemplateFilter);
    }

    @PostMapping("/generate_application_for_practice_report")
    public ResponseEntity<?> generateApplicationForPracticeReport(HttpSession session, @RequestParam String reportType) throws IOException {
        String email = (String) session.getAttribute("email");
        String nameTemplate = "{Ф_И_О}_Прил 4_Заявление на практику_{Тип_программы}_{Сем}.docx";
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Пользователь не авторизован.");
        }
        DocumentTemplateFilter documentTemplateFilter = null;
        if (session.getAttribute("isBachelor").equals(true))
                documentTemplateFilter = template -> template instanceof ApplicationPractice;
        else if (session.getAttribute("studentProfile") == "КМиАД")
            documentTemplateFilter = template -> template instanceof ApplicationPracticeMDA;
        else documentTemplateFilter = template -> template instanceof ApplicationPracticeTRPS;
        return generateReport(nameTemplate, reportType, email, documentTemplateFilter);
    }

    // Маппинг для раздела "Дипломная работа"

    @PostMapping("/generate_supervisor_feedback")
    public ResponseEntity<?> generateSupervisorFeedback(HttpSession session, @RequestParam String reportType) throws IOException {
        String email = (String) session.getAttribute("email");
        Boolean hasCoSupervisor = (Boolean) session.getAttribute("hasCoSupervisor");
        String nameTemplate;
        if (hasCoSupervisor)
            nameTemplate = "{Ф_И_О}_{Направление}_{Тип_программы_латиницей}_VKR_otzyv_2.docx";
        else
            nameTemplate = "{Ф_И_О}_{Направление}_{Тип_программы_латиницей}_VKR_otzyv.docx";
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Пользователь не авторизован.");
        }
        System.out.println(
                ", email: " + email + ", role: " + session.getAttribute("role") +
                ", hasCoSupervisor: " + session.getAttribute("hasCoSupervisor") +
                ", isBachelor: " + session.getAttribute("isBachelor") +
                ", studentProfile: " + session.getAttribute("studentProfile"));
        DocumentTemplateFilter documentTemplateFilter = null;
        if (hasCoSupervisor) {
            if (session.getAttribute("isBachelor").equals(true))
                documentTemplateFilter = template -> template instanceof SupervisorFeedback2;
            else if (session.getAttribute("studentProfile") == "КМиАД")
                documentTemplateFilter = template -> template instanceof SupervisorFeedbackMDA2;
            else documentTemplateFilter = template -> template instanceof SupervisorFeedbackTRPS2;
        } else {
            if (session.getAttribute("isBachelor").equals(true))
                documentTemplateFilter = template -> template instanceof SupervisorFeedback;
            else if (session.getAttribute("studentProfile") == "КМиАД")
                documentTemplateFilter = template -> template instanceof SupervisorFeedbackMDA;
            else documentTemplateFilter = template -> template instanceof SupervisorFeedbackTRPS;
        }
        return generateReport(nameTemplate, reportType, email, documentTemplateFilter);
    }

    //Да, такого слова нет, но отзыв и рецензия на бургерном языке совпадают
    @PostMapping("/generate_review_feedback")
    public ResponseEntity<?> generateReviewFeedback(HttpSession session, @RequestParam String reportType) throws IOException {
        String email = (String) session.getAttribute("email");
        Boolean hasCoSupervisor = (Boolean) session.getAttribute("hasCoSupervisor");
        String nameTemplate = "{Ф_И_О}_{Направление}_{Тип_программы_латиницей}_VKR_recenziya.docx";
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Пользователь не авторизован.");
        }
        DocumentTemplateFilter documentTemplateFilter = null;

        if (session.getAttribute("isBachelor").equals(true))
            documentTemplateFilter = template -> template instanceof ReviewerFeedback;
        else if (session.getAttribute("studentProfile") == "КМиАД")
            documentTemplateFilter = template -> template instanceof ReviewerFeedbackMDA;
        else documentTemplateFilter = template -> template instanceof ReviewerFeedbackTRPS;

        return generateReport(nameTemplate, reportType, email, documentTemplateFilter);
    }

}

