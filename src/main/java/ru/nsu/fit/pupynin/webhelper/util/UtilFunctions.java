package ru.nsu.fit.pupynin.webhelper.util;

import org.apache.jena.query.QuerySolution;
import org.springframework.stereotype.Component;

@Component
public class UtilFunctions {

    public String[] splitFullName(QuerySolution solution, String solutionVarName) {

        if (!solution.contains(solutionVarName)) {
            return new String[]{""}; // Возвращаем пустые строки, если переменная отсутствует
        }

        String fullName = solution.getLiteral(solutionVarName).getString();
        String[] nameParts = fullName.split("\\s+");

        String lastName = nameParts.length > 0 ? nameParts[0] : "";
        String firstName = nameParts.length > 1 ? nameParts[1] : "";
        String middleName = nameParts.length > 2 ? nameParts[2] : "";

        return new String[]{lastName, firstName, middleName};
    }
}
