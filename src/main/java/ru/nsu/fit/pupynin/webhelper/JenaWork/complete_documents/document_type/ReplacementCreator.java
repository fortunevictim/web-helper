package ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type;


import com.github.jsonldjava.utils.Obj;
import com.github.petrovich4j.Case;
import com.github.petrovich4j.Gender;
import com.github.petrovich4j.NameType;
import com.github.petrovich4j.Petrovich;
import org.apache.jena.atlas.test.Gen;
import org.apache.jena.query.QuerySolution;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Extracts data from solution and normalizes it if needed
 */
public sealed interface ReplacementCreator {

    static ReplacementCreator simple(String solutionVarName) {
        return new UriConverterReplacement(new Simple(solutionVarName));
    }

    static ReplacementCreator fullName(String solutionVarName, Case wordCase) {
        return new UriConverterReplacement(new FullNameReplacement(solutionVarName, wordCase));
    }

    static ReplacementCreator gender(String solutionVarName) {
        return new UriConverterReplacement(new GenderStudentReplacement(solutionVarName));

    }

    static ReplacementCreator genderFio(String solutionVarName) {
        return new UriConverterReplacement(new GenderStudentImReplacement(solutionVarName));

    }

    static ReplacementCreator genderForm(String solutionVarName) {
        return new UriConverterReplacement(new GenderFormStudentReplacement(solutionVarName));

    }

    String replacement(QuerySolution solution);
}

final class UriConverterReplacement implements ReplacementCreator {

    private static final char[] ENCODED_CHARS = {'(', ')', '"', '«', '»'};

    private final ReplacementCreator delegateTo;

    UriConverterReplacement(ReplacementCreator delegateTo) {
        this.delegateTo = delegateTo;
    }

    @Override
    public String replacement(QuerySolution solution) {
        String replacement = delegateTo.replacement(solution);
        if (replacement == null) {
            return replacement;
        }
        for (char c : ENCODED_CHARS) {
            String encoded = URLEncoder.encode(String.valueOf(c), StandardCharsets.UTF_8);
            replacement = replacement.replaceAll(encoded, String.valueOf(c));
        }
        return replacement;
    }

}

final class Simple implements ReplacementCreator {

    private final String varName;

    Simple(String varName) {
        this.varName = varName;
    }


    @Override
    public String replacement(QuerySolution solution) {
        if (solution.contains(varName)) {
            System.out.println();
            return solution.getLiteral(varName).getString();
        }
        return null;
    }

}

final class FullNameReplacement implements ReplacementCreator {

    private final String solutionVarName;
    private final Case wordCase;

    FullNameReplacement(String solutionVarName, Case wordCase) {
        this.solutionVarName = solutionVarName;
        this.wordCase = wordCase;
    }

    @Override
    public String replacement(QuerySolution solution) {
        if (!solution.contains(solutionVarName)) {
            return null;
        }
        String fullName = solution.getLiteral(solutionVarName).getString();
        String[] nameChunks = fullName.split(" ");
        if (nameChunks.length == 4) {
            return nameChunks[0] + 'а' + " " + nameChunks[1] + 'а' + " " + nameChunks[2] + " " + nameChunks[3];
        } else if (nameChunks.length == 3 ) {
            Petrovich petrovich = new Petrovich();
            Gender gender = petrovich.gender(nameChunks[2], Gender.Both);
            String lastName = petrovich.say(nameChunks[0], NameType.LastName, gender, wordCase);
            String firstName = petrovich.say(nameChunks[1], NameType.FirstName, gender, wordCase);
            String patronymicName = petrovich.say(nameChunks[2], NameType.PatronymicName, gender, wordCase);
            // the best way because library does not support that case
//            if (Objects.equals(nameChunks[0], "Шабалин")) {
//                return String.format(lastName.replaceFirst("н","на") + " " + firstName + " " +patronymicName);
//            } else
            if (Objects.equals(nameChunks[0], "Кривошея")) {
                return String.format(lastName.replaceFirst("я","и") + " " + firstName + " " +patronymicName);
            } else
                return String.format(lastName + " " + firstName + " " + patronymicName);
        } else if (nameChunks.length == 2) {
            Petrovich petrovich = new Petrovich();
            Gender gender = Gender.Female;
            if (nameChunks[1].matches("Артем|Артём")) gender = Gender.Male;
            String lastName = petrovich.say(nameChunks[0], NameType.LastName, gender, wordCase);
            String firstName = petrovich.say(nameChunks[1], NameType.FirstName, gender, wordCase);
            // the best way because library does not support that case

            return String.format(lastName + " " + firstName);
        }

        else {
            throw new IllegalArgumentException("No full name in variable " + solutionVarName + "; value: " + fullName);
        }
    }
}

final class GenderStudentReplacement implements ReplacementCreator {

    private final String solutionVarName;

    GenderStudentReplacement(String solutionVarName) {
        this.solutionVarName = solutionVarName;
    }

    @Override
    public String replacement(QuerySolution solution) {
        if (!solution.contains(solutionVarName)) {
            return null;
        }
        String fullName = solution.getLiteral(solutionVarName).getString();
        String[] nameChunks = fullName.split(" ");
        if (nameChunks.length == 4) {
            return "Обучающегося";
        } else if (nameChunks.length == 3) {
            Petrovich petrovich = new Petrovich();
            Gender gender = petrovich.gender(nameChunks[2], Gender.Both);
            // the best way because library does not support that case
            switch (gender) {
                case Female -> {
                    return "Обучающейся";
                }
                default -> {
                    return "Обучающегося";
                }
            }
        } else if (nameChunks.length == 2) {
            Gender gender = Gender.Female;
            if (nameChunks[1].matches("Артем|Артём"))
                gender = Gender.Male;
            switch (gender) {
                case Female -> {
                    return "Обучающейся";
                }
                default -> {
                    return "Обучающегося";
                }
            }
        }
        else {
            throw new IllegalArgumentException("No full name in variable " + solutionVarName + "; value: " + fullName);
        }
    }
}


final class GenderFormStudentReplacement implements ReplacementCreator {

    private final String solutionVarName;

    GenderFormStudentReplacement(String solutionVarName) {
        this.solutionVarName = solutionVarName;
    }

    @Override
    public String replacement(QuerySolution solution) {
        if (!solution.contains(solutionVarName)) {
            return null;
        }
        String fullName = solution.getLiteral(solutionVarName).getString();
        String[] nameChunks = fullName.split(" ");
        if (nameChunks.length == 4) {
            return "студенту";
        } else if (nameChunks.length == 3) {
            Petrovich petrovich = new Petrovich();
            Gender gender = petrovich.gender(nameChunks[2], Gender.Both);
            // the best way because library does not support that case
            switch (gender) {
                case Female -> {
                    return "студентке";
                }
                default -> {
                    return "студенту";
                }
            }
        } else if (nameChunks.length == 2) {
            Gender gender = Gender.Female;
            if (nameChunks[1].matches("Артем|Артём"))
                gender = Gender.Male;
            switch (gender) {
                case Female -> {
                    return "студентке";
                }
                default -> {
                    return "студенту";
                }
            }
        } else {
            throw new IllegalArgumentException("No full name in variable " + solutionVarName + "; value: " + fullName);
        }
    }
}

final class GenderStudentImReplacement implements ReplacementCreator {

    private final String solutionVarName;

    GenderStudentImReplacement(String solutionVarName) {
        this.solutionVarName = solutionVarName;
    }

    @Override
    public String replacement(QuerySolution solution) {
        if (!solution.contains(solutionVarName)) {
            return null;
        }
        String fullName = solution.getLiteral(solutionVarName).getString();
        String[] nameChunks = fullName.split(" ");
        if (nameChunks.length == 4) {
            return "Обучающийся";
        } else if (nameChunks.length == 3) {
            Petrovich petrovich = new Petrovich();
            Gender gender = petrovich.gender(nameChunks[2], Gender.Both);
            // the best way because library does not support that case
            switch (gender) {
                case Female -> {
                    return "Обучающаяся";
                }
                default -> {
                    return "Обучающийся";
                }
            }
        } else if (nameChunks.length == 2) {
            Gender gender = Gender.Female;
            if (nameChunks[1].matches("Артем|Артём"))
                gender = Gender.Male;
            switch (gender) {
                case Female -> {
                    return "Обучающаяся";
                }
                default -> {
                    return "Обучающийся";
                }
            }
        } else {
            throw new IllegalArgumentException("No full name in variable " + solutionVarName + "; value: " + fullName);
        }
    }
}

