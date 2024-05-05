package ru.nsu.fit.pupynin.webhelper.controller;


import jakarta.servlet.http.HttpSession;
import org.apache.jena.query.QuerySolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ru.nsu.fit.pupynin.webhelper.TestJena;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.fit.pupynin.webhelper.model.StudentData;
import ru.nsu.fit.pupynin.webhelper.service.UserService;
import ru.nsu.fit.pupynin.webhelper.util.Queries;
import ru.nsu.fit.pupynin.webhelper.util.UtilFunctions;

@Controller
public class DataController {
    @Autowired
    private UserService userService;
    @Autowired
    private UtilFunctions utilFunctions;


    @CrossOrigin
    @RequestMapping(value = "/getStudent", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> getStudent(HttpSession session) {
        String email = (String) session.getAttribute("email");
        String queryString = Queries.GET_STUDENT_FIO_AND_GROUP_QUERY
                .replace("?emailFilter", "\"" + email + "\"");

        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();
        for (QuerySolution solution : resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "фио_студента");
            for (String namePart : baseNameParts) {
                obj.put(String.valueOf(obj.size()), namePart);
            }
            String group = solution.getLiteral("группа_студента").getString();
            obj.put(String.valueOf(obj.size()), group);
            jsonResultSet.add(obj);

        }
        return ResponseEntity.ok().body(jsonResultSet);
    }

    @CrossOrigin
    @RequestMapping(value = "/getSupervisor", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> getSupervisor(HttpSession session) {
        String email = (String) session.getAttribute("email");
        String queryString = Queries.GET_SUPERVISOR_INFO_QUERY
                .replace("?emailFilter", "\"" + email + "\"");

        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();
        for (QuerySolution solution : resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "sup_fio");
            for (String namePart : baseNameParts) {
                obj.put(String.valueOf(obj.size()), namePart);
            }
            String[] sup = {"sup_title", "sup_degree", "sup_badge"};
            for (int i = 0; i < sup.length; i++){
                if(solution.getLiteral(sup[i]) != null) {
                    String var = solution.getLiteral(sup[i]).getString();
                    obj.put(String.valueOf(obj.size()), var);
                }
            }
            jsonResultSet.add(obj);
        }
        return ResponseEntity.ok().body(jsonResultSet);
    }

    @CrossOrigin
    @RequestMapping(value = "/getSupervisorNSU", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> getSupervisorNSU(HttpSession session) {
        String email = (String) session.getAttribute("email");
        String queryString = Queries.GET_SUPERVISOR_NSU_INFO_QUERY
                .replace("?emailFilter", "\"" + email + "\"");

        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();
        for (QuerySolution solution : resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "sup_nsu_fio");
            for (String namePart : baseNameParts) {
                obj.put(String.valueOf(obj.size()), namePart);
            }
            String[] sup = {"sup_nsu_title", "sup_nsu_degree", "sup_nsu_badge"};
            for (int i = 0; i < sup.length; i++){
                if(solution.getLiteral(sup[i]) != null) {
                    String var = solution.getLiteral(sup[i]).getString();
                    obj.put(String.valueOf(obj.size()), var);
                }
            }
            jsonResultSet.add(obj);
        }
        return ResponseEntity.ok().body(jsonResultSet);
    }

    @CrossOrigin
    @RequestMapping(value = "/getSupervisorOrg", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> getSupervisorOrg(HttpSession session) {
        String email = (String) session.getAttribute("email");
        String queryString = Queries.GET_SUPERVISOR_ORG_INFO_QUERY
                .replace("?emailFilter", "\"" + email + "\"");

        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();
        for (QuerySolution solution : resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "sup_org_fio");
            for (String namePart : baseNameParts) {
                obj.put(String.valueOf(obj.size()), namePart);
            }
            String[] sup = {"sup_org_title", "sup_org_degree", "sup_org_badge"};
            for (int i = 0; i < sup.length; i++){
                if(solution.getLiteral(sup[i]) != null) {
                    String var = solution.getLiteral(sup[i]).getString();
                    obj.put(String.valueOf(obj.size()), var);
                }
            }
            jsonResultSet.add(obj);
        }
        return ResponseEntity.ok().body(jsonResultSet);
    }

    @CrossOrigin
    @RequestMapping(value = "/getCoSupervisor", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> getCoSupervisor(HttpSession session) {
        String email = (String) session.getAttribute("email");
        String queryString = Queries.GET_CO_SUPERVISOR_INFO_QUERY
                .replace("?emailFilter", "\"" + email + "\"");

        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();
        for (QuerySolution solution : resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "co_sup_fio");
            for (String namePart : baseNameParts) {
                obj.put(String.valueOf(obj.size()), namePart);
            }
            String[] sup = {"co_sup_title", "co_sup_degree", "co_sup_badge"};
            for (int i = 0; i < sup.length; i++){
                if(solution.getLiteral(sup[i]) != null) {
                    String var = solution.getLiteral(sup[i]).getString();
                    obj.put(String.valueOf(obj.size()), var);
                }
            }
            jsonResultSet.add(obj);
        }
        return ResponseEntity.ok().body(jsonResultSet);
    }

    @CrossOrigin
    @RequestMapping(value = "/getConsultant", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> getConsultant(HttpSession session) {
        String email = (String) session.getAttribute("email");
        String queryString = Queries.GET_CONSULTANT_INFO_QUERY
                .replace("?emailFilter", "\"" + email + "\"");

        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();
        for (QuerySolution solution : resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "consultant_fio");
            for (String namePart : baseNameParts) {
                obj.put(String.valueOf(obj.size()), namePart);
            }
            String[] sup = {"consultant_title", "consultant_degree", "consultant_badge"};
            for (int i = 0; i < sup.length; i++){
                if(solution.getLiteral(sup[i]) != null) {
                    String var = solution.getLiteral(sup[i]).getString();
                    obj.put(String.valueOf(obj.size()), var);
                }
            }
            jsonResultSet.add(obj);
        }
        return ResponseEntity.ok().body(jsonResultSet);
    }

    @CrossOrigin
    @RequestMapping(value = "/getReviewer", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> getReviewer(HttpSession session) {
        String email = (String) session.getAttribute("email");
        String queryString = Queries.GET_REVIEWER_INFO_QUERY
                .replace("?emailFilter", "\"" + email + "\"");

        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();
        for (QuerySolution solution : resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "reviewer_fio");
            for (String namePart : baseNameParts) {
                obj.put(String.valueOf(obj.size()), namePart);
            }
            String[] sup = {"reviewer_title", "reviewer_degree", "reviewer_badge"};
            for (int i = 0; i < sup.length; i++){
                if(solution.getLiteral(sup[i]) != null) {
                    String var = solution.getLiteral(sup[i]).getString();
                    obj.put(String.valueOf(obj.size()), var);
                }
            }
            jsonResultSet.add(obj);
        }
        return ResponseEntity.ok().body(jsonResultSet);
    }

    ///////////////////////////////
    //// CONTROL PAGE FUNCTIONS ///
    ///////////////////////////////

    //Вызывается в control.js
    @CrossOrigin
    @RequestMapping(value = "/getStudents", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> findStudents() {
        String queryString = Queries.GET_STUDENTS_ALL;
        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();
        int i = 0;
        for (QuerySolution solution: resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "фио");
            String fullName = String.join(" ", baseNameParts);
            obj.put(String.valueOf(i), fullName);
            jsonResultSet.add(obj);
            i++;
        }
        return ResponseEntity.ok().body(jsonResultSet);
    }

    @CrossOrigin
    @RequestMapping(value = "/getStudentsWithEmailControl", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> getStudentsWithEmailControl(@RequestParam String profile) {

        String queryString = Queries.GET_STUDENTS_WITH_GROUPS_CONTROL
                .replace("?profileFilter", profile);
        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();
        for (QuerySolution solution: resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "фио_студента");
            String fullName = String.join(" ", baseNameParts);
            String email = solution.getLiteral("почта").getString(); // Извлечение почты
            // Добавляем пару ФИО и почта в объект
            obj.put("fullName", fullName);
            obj.put("email", email);
            jsonResultSet.add(obj);
        }
        return ResponseEntity.ok().body(jsonResultSet);
    }

    //Чтобы не писать отдельный запрос для взятия только группы, используется sparql запрос
    //который применяется для отображения данных на страницах practice и diploma
    @CrossOrigin
    @RequestMapping(value = "/getStudentControl", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> getStudentControl(@RequestParam String email) {
        String queryString = Queries.GET_STUDENT_FIO_AND_GROUP_QUERY
                .replace("?emailFilter", "\"" + email + "\"");

        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();
        for (QuerySolution solution : resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "фио_студента");
            String fullName = String.join(" ", baseNameParts);

            String group = solution.getLiteral("группа_студента").getString();
            obj.put("fullName", fullName);
            obj.put("group", group);
            jsonResultSet.add(obj);
        }
        return ResponseEntity.ok().body(jsonResultSet);
    }

    @CrossOrigin
    @RequestMapping(value = "/getCoSupervisorControl", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> getCoSupervisorControl() {
        String queryString = Queries.GET_CO_SUPERVISOR_CONTROL;

        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();
        for (QuerySolution solution : resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "cosup_fio");
            String fullName = String.join(" ", baseNameParts);

            obj.put("fullName", fullName);
            jsonResultSet.add(obj);
        }
        return ResponseEntity.ok().body(jsonResultSet);
    }

    @CrossOrigin
    @RequestMapping(value = "/getSupervisorControl", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> getSupervisorControl() {
        String queryString = Queries.GET_SUPERVISOR_CONTROL;

        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();
        for (QuerySolution solution : resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "supervisor_fio");
            String fullName = String.join(" ", baseNameParts);

            obj.put("fullName", fullName);
            jsonResultSet.add(obj);
        }
        return ResponseEntity.ok().body(jsonResultSet);
    }

    @CrossOrigin
    @RequestMapping(value = "/getConsultantControl", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> getConsultantControl() {
        String queryString = Queries.GET_CONSULTANT_CONTROL;

        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();
        for (QuerySolution solution : resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "consultant_fio");
            String fullName = String.join(" ", baseNameParts);

            obj.put("fullName", fullName);
            jsonResultSet.add(obj);
        }
        return ResponseEntity.ok().body(jsonResultSet);
    }

    @CrossOrigin
    @RequestMapping(value = "/getReviewerControl", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> getReviewerControl() {
        String queryString = Queries.GET_REVIEWER_CONTROL;

        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();
        for (QuerySolution solution : resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "reviewer_fio");
            String fullName = String.join(" ", baseNameParts);

            obj.put("fullName", fullName);
            jsonResultSet.add(obj);
        }
        return ResponseEntity.ok().body(jsonResultSet);
    }

    @CrossOrigin
    @RequestMapping(value = "/getPracticeSupervisorNSUControl", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> getPracticeSupervisorNSUControl() {
        String queryString = Queries.GET_PRACTICE_SUPERVISOR_NSU_CONTROL;

        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();
        for (QuerySolution solution : resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "practice_supervisor_NSU_fio");
            String fullName = String.join(" ", baseNameParts);

            obj.put("fullName", fullName);
            jsonResultSet.add(obj);
        }
        return ResponseEntity.ok().body(jsonResultSet);
    }

    @CrossOrigin
    @RequestMapping(value = "/getPracticeSupervisorOrgControl", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> getPracticeSupervisorOrgControl() {
        String queryString = Queries.GET_PRACTICE_SUPERVISOR_ORG_CONTROL;

        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();
        for (QuerySolution solution : resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "practice_supervisor_org_fio");
            String fullName = String.join(" ", baseNameParts);

            obj.put("fullName", fullName);
            jsonResultSet.add(obj);
        }
        return ResponseEntity.ok().body(jsonResultSet);
    }

    @CrossOrigin
    @RequestMapping(value = "/getStudentCurrentInfoControl", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> getStudentCurrentInfoControl(@RequestParam String email) {
        String queryString = Queries.GET_STUDENT_LISTS_DATA_CONTROL
                .replace("?emailFilter", "\"" + email + "\"");

        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();
        System.out.println(resultSet);
        for (QuerySolution solution : resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "student_fio");
            String fullName = String.join(" ", baseNameParts);
            String supervisor = String.join(" ",
                    utilFunctions.splitFullName(solution, "supervisor_fio"));
            String cosup = String.join(" ",
                    utilFunctions.splitFullName(solution, "cosup_fio"));
            String consultant = String.join(" ",
                    utilFunctions.splitFullName(solution, "consultant_fio"));
            String reviewer = String.join(" ",
                    utilFunctions.splitFullName(solution, "reviewer_fio"));
            String practice_sup_NSU = String.join(" ",
                    utilFunctions.splitFullName(solution, "practice_supervisor_NSU_fio"));
            String practice_sup_org = String.join(" ",
                    utilFunctions.splitFullName(solution, "practice_supervisor_org_fio"));
            obj.put("fullName", fullName);
            obj.put("supervisor", supervisor);
            obj.put("coSupervisor", cosup);
            obj.put("consultant", consultant);
            obj.put("reviewer", reviewer);
            obj.put("practice_sup_NSU", practice_sup_NSU);
            obj.put("practice_sup_org", practice_sup_org);
            System.out.println(obj);
            jsonResultSet.add(obj);
        }
        return ResponseEntity.ok().body(jsonResultSet);
    }

    @CrossOrigin
    @RequestMapping(value = "/getStudentTextDataControl", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> getStudentTextDataControl(@RequestParam String email) {
        String queryString = Queries.GET_STUDENT_TEXT_DATA_CONTROL
                .replace("?emailFilter", "\"" + email + "\"");

        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        List<JSONObject> jsonResultSet = new ArrayList<>();

        for (QuerySolution solution : resultSet) {
            JSONObject obj = new JSONObject();
            String[] baseNameParts = utilFunctions.splitFullName(solution, "fio");
            String fullName = String.join(" ", baseNameParts);
            String group = solution.getLiteral("group").getString();
            String thesis = solution.getLiteral("thesis").getString();
            String qemail = solution.getLiteral("email").getString();
            String practiceOrder = solution.getLiteral("practiceOrder").getString();
            String thesisOrder = solution.getLiteral("thesisOrder").getString();
            String internshipPlaceShort = solution.getLiteral("internshipPlaceShort").getString();
            String internshipPlaceFull = solution.getLiteral("internshipPlaceFull").getString();
            String internshipPlace = solution.getLiteral("internshipPlace").getString();
            String profile = solution.getLiteral("profile").getString();

            obj.put("fio", fullName);
            obj.put("group", group);
            obj.put("thesis", thesis);
            obj.put("email", qemail);
            obj.put("practiceOrder", practiceOrder);
            obj.put("thesisOrder", thesisOrder);
            obj.put("internshipPlaceShort", internshipPlaceShort);
            obj.put("internshipPlaceFull", internshipPlaceFull);
            obj.put("internshipPlace", internshipPlace);
            obj.put("profile", profile);
            jsonResultSet.add(obj);
        }

        return ResponseEntity.ok().body(jsonResultSet);
    }

    /// Getting data from Control page
    @PostMapping("/submitStudentData")
    public ResponseEntity<String> submitStudentData(@RequestBody StudentData studentData, HttpSession session) {
        String oldEmail = studentData.getOldEmail();

        String queryStringText = Queries.GET_STUDENT_TEXT_DATA_CONTROL
                .replace("?emailFilter", "\"" + oldEmail + "\"");
        List<QuerySolution> resultSetText = TestJena.findStudentInfo(queryStringText);

        String queryStringList = Queries.GET_STUDENT_LISTS_DATA_CONTROL
                .replace("?emailFilter", "\"" + oldEmail + "\"");
        List<QuerySolution> resultSetList = TestJena.findStudentInfo(queryStringList);

        StudentData originStudentData = new StudentData();
        initStudentData(originStudentData, resultSetText, resultSetList);

        TestJena.areControlDataEqual(originStudentData, studentData);

        return ResponseEntity.ok("Data received successfully");
    }

    private void initStudentData(StudentData origin, List<QuerySolution> resultSetText,
                                 List<QuerySolution> resultSetList){
        for (QuerySolution solution: resultSetText) {
            String[] baseNameParts = utilFunctions.splitFullName(solution, "fio");
            origin.setStudentFio(String.join(" ", baseNameParts));
            origin.setStudentGroup(solution.getLiteral("group").getString());
            origin.setThesis(solution.getLiteral("thesis").getString());
            origin.setEmail(solution.getLiteral("email").getString());
            origin.setPracticeOrder(solution.getLiteral("practiceOrder").getString());
            origin.setThesisOrder(solution.getLiteral("thesisOrder").getString());
            origin.setInternshipPlaceShort(solution.getLiteral("internshipPlaceShort").getString());
            origin.setInternshipPlaceFull(solution.getLiteral("internshipPlaceFull").getString());
            origin.setInternshipPlace(solution.getLiteral("internshipPlace").getString());
            origin.setProfile(solution.getLiteral("profile").getString());
        }
        for (QuerySolution solution: resultSetList) {
            origin.setSupervisor(String.join(" ",
                    utilFunctions.splitFullName(solution, "supervisor_fio")));
            origin.setCoSupervisor(String.join(" ",
                    utilFunctions.splitFullName(solution, "cosup_fio")));
            origin.setConsultant(String.join(" ",
                    utilFunctions.splitFullName(solution, "consultant_fio")));
            origin.setReviewer(String.join(" ",
                    utilFunctions.splitFullName(solution, "reviewer_fio")));
            origin.setPractice_sup_NSU(String.join(" ",
                    utilFunctions.splitFullName(solution, "practice_supervisor_NSU_fio")));
            origin.setPractice_sup_org(String.join(" ",
                    utilFunctions.splitFullName(solution, "practice_supervisor_org_fio")));
        }
    }
}
