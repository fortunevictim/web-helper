package ru.nsu.fit.pupynin.webhelper.service;

import org.apache.jena.rdf.model.Literal;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pupynin.webhelper.TestJena;
import org.apache.jena.query.QuerySolution;
import ru.nsu.fit.pupynin.webhelper.util.PasswordGenerator;
import ru.nsu.fit.pupynin.webhelper.util.Queries;

import java.util.List;

@Service
public class UserService {
    private static Integer PASS_LENGTH = 8;

    //СМЕНА РОЛИ ADMINADMIN
    public String getUserRole(String email) {
        String queryString = Queries.GET_USER_ROLE_QUERY.replace("?emailFilter", "\"" + email + "\"");
        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        System.out.println("current role is : " + (resultSet.isEmpty() ? "user" : "admin"));
        return resultSet.isEmpty() ? "user" : "admin";
        // Если пользователь не найден в классе руководителей, считаем его обычным пользователем
    }

    public boolean isUserRegistered(String email) {
        String queryString = Queries.GET_USER_PASSWORD_QUERY
                .replace("?emailFilter", "\"" + email + "\"");
        System.out.println("email: " + email);
        System.out.println(queryString);
        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        String storedPassword = null;
        for (QuerySolution solution: resultSet) {
            storedPassword = solution.getLiteral("password").getString();
        }
        System.out.println("passwd: " + storedPassword);
        return !storedPassword.equals("");
    }
    public boolean isEmailValid(String email) {
        String queryString = Queries.GET_USER_EMAIL_QUERY.replace("?emailFilter", "\"" + email + "\"");
        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        return !resultSet.isEmpty();
    }

    public boolean isUserValid(String email, String password) {
        String role = getUserRole(email);
        if (role == null) {
            return false;
        }
        String queryString = Queries.IS_USER_VALID.replace("?emailFilter", "\"" + email + "\"");

        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        if (resultSet.isEmpty()) {
            return false;
        }
        String storedPassword = resultSet.get(0).getLiteral("password").getString();
        System.out.printf("for %s user password is %s%n", email, storedPassword);
        return password.equals(storedPassword);
        //в реальности нужно использовать безопасное сравнение + шифрование
    }

    public String generatePassword() {
        return PasswordGenerator.generatePassword(PASS_LENGTH);
    }

    public void updateUserPassword(String email, String password) {
        String updateQuery = Queries.UPDATE_USER_PASSWORD_QUERY
                .replace("?newPassword", "\"" + password + "\"")
                .replace("?emailFilter", "\"" + email + "\"");
        TestJena.executeUpdate(updateQuery);
    }

    public String getUserPassword(String email){
        String queryString = Queries.GET_USER_PASSWORD_QUERY
                .replace("?emailFilter", "\"" + email + "\"");
        System.out.println(queryString);
        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        String storedPassword = null;
        for (QuerySolution solution: resultSet) {
            storedPassword = solution.getLiteral("password").getString();
        }
        System.out.println("passwd: " + storedPassword);
        return storedPassword;
    }
    public String getUserGroup(String email) {
        String queryString = Queries.GET_USER_GROUP_QUERY
                .replace("?emailFilter", "\"" + email + "\"");
        System.out.println(queryString);
        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        if (resultSet.isEmpty()) {
            System.out.println("rset is empty");
            return null;
        }
        String group = resultSet.get(0).getLiteral("group").getString();
        System.out.println("group is: " + group);
        return group;
    }
    public String getUserFIO(String email) {
        String queryString = Queries.GET_USER_FIO_QUERY
                .replace("?emailFilter", "\"" + email + "\"");
        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        if (resultSet.isEmpty()) {
            return null;
        }
        return resultSet.get(0).getLiteral("fio").getString();
    }

    public String getUserCoSupervisor(String email) {
        String queryString = Queries.GET_STUDENT_CO_SUPERVISOR_QUERY
                .replace("?emailFilter", "\"" + email + "\"");
        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        if (resultSet.isEmpty()) {
            return null;
        }
        Literal cosupFioLiteral = resultSet.get(0).getLiteral("cosup_fio");
        return cosupFioLiteral != null ? cosupFioLiteral.getString() : null;
    }

    public String getStudentProfile(String email) {
        String queryString = Queries.GET_STUDENT_MASTER_PROFILE_QUERY
                .replace("?emailFilter", "\"" + email + "\"");
        List<QuerySolution> resultSet = TestJena.findStudentInfo(queryString);
        if (resultSet.isEmpty()) {
            return "1"; //я уже даже не помню зачем я это добавил,
                        // но суть в том чтобы определить чтобы по логу определить
                        // что профиль нормально загрузился
        }
        Literal profile = resultSet.get(0).getLiteral("profile");
        System.out.println("PROFILE: " + profile.getString());
        return profile != null ? profile.getString() : "1";
    }


}
