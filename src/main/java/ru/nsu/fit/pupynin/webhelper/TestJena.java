package ru.nsu.fit.pupynin.webhelper;

import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.shared.JenaException;
import org.apache.jena.update.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.util.ResourceUtils;
import ru.nsu.fit.pupynin.webhelper.model.StudentData;
import ru.nsu.fit.pupynin.webhelper.util.Queries;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestJena {

    public static QueryExecution qe;
    public static String ontoSource = "http://www.semanticweb.org/oleyn/ontologies/2022/4/кафедра#";
    public static String ontoFile = "D:\\main_students_all.owl";
    //TODO change to local path

    public static ResultSet execQuery(String queryString) {
        Model ontoModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        System.out.println("Onto loaded");
        try {
            InputStream in = FileManager.get().open(ontoFile);
            try {
                ontoModel.read(in, null);
                Query query = QueryFactory.create(queryString);
                qe = QueryExecutionFactory.create(query, ontoModel);
                ResultSet results = qe.execSelect();
                return results;

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Ontology " + ontoFile + " loaded.");
        } catch (JenaException je) {
            System.err.println("ERROR" + je.getMessage());
            je.printStackTrace();
        }
        return null;
    }

    public static List<QuerySolution> findStudentInfo(String queryString) {
        ResultSet resultSet = execQuery(queryString);
        List<QuerySolution> list = new ArrayList<>();

        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            list.add(solution);
        }
        // Этот импортант не относится к текущему варианту проекта, поскольку очередь
        // отображения большая и вызывается подряд несколько раз подряд, то иногда
        // программа не успевает открывать QueryExecution'ы заново.
        // Important ‑ free up resources used running the query
        // qe.close();

        return list;
    }

    public static void executeUpdate(String updateQuery) {
        Model model = ModelFactory.createDefaultModel();
        model.read(ontoFile, "RDF/XML");
        System.out.println("Model loaded");

        Dataset dataset = DatasetFactory.create(model);
        System.out.println("dataset created");

        UpdateAction.parseExecute(updateQuery, dataset);
        System.out.println("parseExecute done");
        try (FileWriter writer = new FileWriter(ontoFile)) {
            model.write(writer, "RDF/XML");
            System.out.println("Ontology " + ontoFile + " updated and saved.");
        } catch (IOException e) {
            System.err.println("ERROR: Failed to write to file - " + e.getMessage());
        }
        System.out.println("Ontology " + ontoFile + " updated and saved.");
    }

    public static void updateRDFIndividual(String originData, String data) {
        Model model = ModelFactory.createDefaultModel();
        model.read(ontoFile, "RDF/XML");
        System.out.println("MODEL READ");

        Resource originalResource = model.getResource(
                ontoSource + originData

        );
        Resource renamed =
                ResourceUtils.renameResource(originalResource,
                        ontoSource + data
                );
        System.out.println("originData: " + originData);
        System.out.println("newData: " + data);
        try (FileWriter writer = new FileWriter(ontoFile)) {
            model.write(writer, "RDF/XML");
            System.out.println("Ontology" + ontoFile + " updated and saved.");
        } catch (IOException e) {
            System.err.println("ERROR: Failed to write to file - " + e.getMessage());
        }
    }
    public static void updateRDFRelationship(Model model, StudentData originData, StudentData data) {

    }

    //аргументы (пример) - (по какой теме вкр меняем науча, науч у которого убираем,
    // науч которому добавляем, отношения которые добавляем)
    // ("МояТемаВкр", "ИвановИванИванович", "ПетровПетрПетрович", "согласовывает")
    public static void updateSupervisorsRelationships(String originData, String removeSubject,
                                                      String addSubject, String relation) {
        Model model = ModelFactory.createDefaultModel();
        model.read(ontoFile, "RDF/XML");
        System.out.println("MODEL READ");
        Property objectToSubjectRelation = model.createProperty(ontoSource + relation);

        Resource removeSubjectResource = model.getResource(ontoSource + removeSubject);
        Resource originResource = model.getResource(ontoSource + originData);
        if (removeSubject != null) {
            model.remove(removeSubjectResource, objectToSubjectRelation, originResource);
            System.out.println("Removed " + relation + " relationship from " + removeSubject);
        }

        Resource addSubjectResource = model.getResource(ontoSource + addSubject);
        if (addSubject != null) {
            model.add(addSubjectResource, objectToSubjectRelation, originResource);
            System.out.println("Added "+ relation +" relationship to " + addSubject);
        }

        // Сохраняем изменения в файл
        try (FileWriter writer = new FileWriter(ontoFile)) {
            model.write(writer, "RDF/XML");
            System.out.println("Ontology " + ontoFile + " updated and saved.");
        } catch (IOException e) {
            System.err.println("ERROR: Failed to write to file - " + e.getMessage());
        }
    }


    // вызывается так: ("отношения", "кто был руководителем", "кто стал руководителем", "студент")
    public static void updateRelationship(String relation, String oldResource, String newResource,
                                          String subject) {
        Model model = ModelFactory.createDefaultModel();
        model.read(ontoFile, "RDF/XML");
        System.out.println("MODEL READ");
        // Создаем отношение на основе переданного параметра
        Property property = model.createProperty(ontoSource + relation);

        // Удаляем отношение у текущего индивида
        Resource subjectResource = model.getResource(ontoSource + subject);
        Resource oldResourceObj = model.getResource(ontoSource + oldResource);
        if (!Objects.equals(oldResourceObj, null)) {
            model.remove(subjectResource, property, oldResourceObj);
            System.out.println("Removed '" + relation + "' relationship from " + subject + " to " + oldResource);
        }
        // Добавляем отношение к новому индивиду
        Resource newResourceObj = model.getResource(ontoSource + newResource);
        if (!Objects.equals(newResourceObj, null)) {
            model.add(subjectResource, property, newResourceObj);
            System.out.println("Added '" + relation + "' relationship from " + subject + " to " + newResource);
        }
        // Сохраняем изменения в файл
        try (FileWriter writer = new FileWriter(ontoFile)) {
            model.write(writer, "RDF/XML");
            System.out.println("Ontology " + ontoFile + " updated and saved.");
        } catch (IOException e) {
            System.err.println("ERROR: Failed to write to file - " + e.getMessage());
        }
    }


    public static String transformString(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();
        Pattern punctuationPattern = Pattern.compile("[.,\\-!?]");
        for (String word : words) {
            if (word.length() > 0) {
                Matcher matcher = punctuationPattern.matcher(word);
                if (!matcher.find()) {
                    String transformedWord = word.substring(0, 1).toUpperCase() + word.substring(1);
                    result.append(transformedWord);
                } else {
                    result.append(word);
                }
            }
        }
        return result.toString().replaceAll("\\s+", " ");
    }


    //Сначала должны обязательно обрабатываться поля без RDF-редактирования (группа, почта,
    // приказ, распоряжение, наименование орг., местро практики, место парктики полное, профиль)
    //а затем - остальные (фио, тезис + 6 списков), поскольку иначе произойдет может произойти подкладка тезисов.
    public static void areControlDataEqual(StudentData originData, StudentData newData) {
        if (originData == null || newData == null) {
            System.out.println("some of the StudentData are null");
            return;
        }
        //data that need to be edited with RDF too
        //Fio
        String originValue = originData.getStudentFio();
        String dataValue = newData.getStudentFio();
        if (!Objects.equals(originValue, dataValue)) {
            System.out.println("\""+originValue+"\"" + " : " + "\"" + dataValue+"\"");
            String queryString = Queries.UPDATE_STUDENT_FIO
                    .replace("?emailFilter", "\"" + originData.getEmail() + "\"")
                    .replace("?fioFilter", "\"" + newData.getStudentFio() + "\"");
            executeUpdate(queryString);
            updateRDFIndividual(transformString(originValue), transformString(dataValue));
        }
        else System.out.println("FIO equal");
        //thesis
        originValue = originData.getThesis();
        dataValue = newData.getThesis();
        if (!Objects.equals(originValue, dataValue)) {
            System.out.println("\""+originValue+"\"" + " : " + "\"" + dataValue+"\"");
            String queryString = Queries.UPDATE_THESIS_FKW_AND_STUDENT
                    .replace("?emailFilter", "\"" + originData.getEmail() + "\"")
                    .replace("?titleFilter", "\"" + newData.getThesis() + "\"");
            executeUpdate(queryString);
            updateRDFIndividual(transformString(originValue), transformString(dataValue));
        }
        else System.out.println("thesis equal");
        //supervisor
        originValue = originData.getSupervisor();
        dataValue = newData.getSupervisor();
        if (!Objects.equals(originValue, dataValue)) {
            System.out.println("\""+originValue+"\"" + " : " + "\"" + dataValue+"\"");
            updateSupervisorsRelationships(transformString(newData.getThesis()),
                    transformString(originData.getSupervisor()),
                    transformString(newData.getSupervisor()), "согласовывает");
        }
        else System.out.println("supervisor equal");
        //coSupervisor
        originValue = originData.getCoSupervisor();
        dataValue = newData.getCoSupervisor();
        if (!Objects.equals(originValue, dataValue)) {
            System.out.println("\""+originValue+"\"" + " : " + "\"" + dataValue+"\"");
            updateSupervisorsRelationships(transformString(newData.getThesis()),
                    transformString(originData.getCoSupervisor()),
                    transformString(newData.getCoSupervisor()), "руководит_с");
        }
        else System.out.println("coSupervisor equal");
        //consultant
        originValue = originData.getConsultant();
        dataValue = newData.getConsultant();
        if (!Objects.equals(originValue, dataValue)) {
            System.out.println("\""+originValue+"\"" + " : " + "\"" + dataValue+"\"");
            updateSupervisorsRelationships(transformString(newData.getThesis()),
                    transformString(originData.getConsultant()),
                    transformString(newData.getConsultant()), "консультирует");
        }
        else System.out.println("consultant equal");
        //reviewer
        originValue = originData.getReviewer();
        dataValue = newData.getReviewer();
        System.out.println("Thesis old : " + transformString(originData.getThesis()));
        System.out.println("Thesis new : " + transformString(newData.getThesis()));
        System.out.println("Reviewer old : " + transformString(originData.getReviewer()));
        System.out.println("Reviewer new : " + transformString(newData.getReviewer()));
        if (!Objects.equals(originValue, dataValue)) {
            System.out.println("\""+originValue+"\"" + " : " + "\"" + dataValue+"\"");
            updateSupervisorsRelationships(transformString(newData.getThesis()),
                    transformString(originData.getReviewer()),
                    transformString(newData.getReviewer()), "рецензирует");
        }
        else System.out.println("reviewer equal");
        //practiceSupervisorNSU
        originValue = originData.getPractice_sup_NSU();
        dataValue = newData.getPractice_sup_NSU();
        if (!Objects.equals(originValue, dataValue)) {
            System.out.println("\""+originValue+"\"" + " : " + "\"" + dataValue+"\"");
            updateRelationship("на_НГУ_практике_у",
                    transformString(originData.getPractice_sup_NSU()),
                    transformString(newData.getPractice_sup_NSU()),
                    transformString(originData.getStudentFio()));
        }
        else System.out.println("practiceSupervisorNSU equal");
        //practiceSupervisorOrg
        originValue = originData.getPractice_sup_org();
        dataValue = newData.getPractice_sup_org();
        if (!Objects.equals(originValue, dataValue)) {
            System.out.println("\""+originValue+"\"" + " : " + "\"" + dataValue+"\"");
            updateRelationship("на_орг_практике_у",
                    transformString(originData.getPractice_sup_org()),
                    transformString(newData.getPractice_sup_org()),
                    transformString(originData.getStudentFio()));
        }
        else System.out.println("practiceSupervisorOrg equal");
        // data editable with only SPARQL query
        //group
        originValue = originData.getStudentGroup();
        dataValue = newData.getStudentGroup();
        if (!Objects.equals(originValue, dataValue)) {
            System.out.println("\""+originValue+"\"" + " : " + "\"" + dataValue+"\"");
            String queryString = Queries.UPDATE_STUDENT_GROUP
                    .replace("?emailFilter", "\"" + originData.getEmail() + "\"")
                    .replace("?groupFilter", "\"" + newData.getStudentGroup() + "\"");
            executeUpdate(queryString);
        }
        else System.out.println("group equal");
        //email
        originValue = originData.getEmail();
        dataValue = newData.getEmail();
        if (!originValue.equals(dataValue)) {
            System.out.println("\""+originValue+"\"" + " : " + "\"" + dataValue+"\"");
            String queryString = Queries.UPDATE_STUDENT_EMAIL
                    .replace("?emailFilter", "\"" + originData.getEmail() + "\"")
                    .replace("?newEmailFilter", "\"" + newData.getEmail() + "\"");
            executeUpdate(queryString);
        }
        else System.out.println("email equal");
        //Practice Order
        originValue = originData.getPracticeOrder();
        dataValue = newData.getPracticeOrder();
        if (!originValue.equals(dataValue)) {
            System.out.println("\""+originValue+"\"" + " : " + "\"" + dataValue+"\"");
            String queryString = Queries.UPDATE_STUDENT_PRACTICE_ORDER
                    .replace("?emailFilter", "\"" + originData.getEmail() + "\"")
                    .replace("?practiceOrderFilter", "\"" + newData.getPracticeOrder() + "\"");
            executeUpdate(queryString);
        }
        else System.out.println("practiceOrder equal");
        //Thesis Order
        originValue = originData.getThesisOrder();
        dataValue = newData.getThesisOrder();
        if (!originValue.equals(dataValue)) {
            System.out.println("\""+originValue+"\"" + " : " + "\"" + dataValue+"\"");
            String queryString = Queries.UPDATE_STUDENT_THESIS_ORDER
                    .replace("?emailFilter", "\"" + originData.getEmail() + "\"")
                    .replace("?thesisOrderFilter", "\"" + newData.getThesisOrder() + "\"");
            executeUpdate(queryString);
        }
        else System.out.println("thesisOrder equal");
        //internshipPlaceShort
        originValue = originData.getInternshipPlaceShort();
        dataValue = newData.getInternshipPlaceShort();
        if (!originValue.equals(dataValue)) {
            System.out.println("\""+originValue+"\"" + " : " + "\"" + dataValue+"\"");
            String queryString = Queries.UPDATE_STUDENT_INTERNSHIP_PLACE_SHORT
                    .replace("?emailFilter", "\"" + originData.getEmail() + "\"")
                    .replace("?internshipPlaceShortFilter", "\"" + newData.getInternshipPlaceShort() + "\"");
            executeUpdate(queryString);
        }
        else System.out.println("internshipPlaceShort equal");
        //internshipPlaceFull
        originValue = originData.getInternshipPlaceFull();
        dataValue = newData.getInternshipPlaceFull();
        if (!originValue.equals(dataValue)) {
            System.out.println("\""+originValue+"\"" + " : " + "\"" + dataValue+"\"");
            String queryString = Queries.UPDATE_STUDENT_INTERNSHIP_PLACE_FULL
                    .replace("?emailFilter", "\"" + originData.getEmail() + "\"")
                    .replace("?internshipPlaceFullFilter", "\"" + newData.getInternshipPlaceFull() + "\"");
            executeUpdate(queryString);
        }
        else System.out.println("internshipPlaceFull equal");
        //internshipPlace
        originValue = originData.getInternshipPlace();
        dataValue = newData.getInternshipPlace();
        if (!originValue.equals(dataValue)) {
            System.out.println("\""+originValue+"\"" + " : " + "\"" + dataValue+"\"");
            String queryString = Queries.UPDATE_STUDENT_INTERNSHIP_PLACE
                    .replace("?emailFilter", "\"" + originData.getEmail() + "\"")
                    .replace("?internshipPlaceFilter", "\"" + newData.getInternshipPlace() + "\"");
            executeUpdate(queryString);
        }
        else System.out.println("internshipPlace equal");
        //profile
        originValue = originData.getProfile();
        dataValue = newData.getProfile();
        if (!originValue.equals(dataValue)) {
            System.out.println("\""+originValue+"\"" + " : " + "\"" + dataValue+"\"");
            String queryString = Queries.UPDATE_STUDENT_PROFILE
                    .replace("?emailFilter", "\"" + originData.getEmail() + "\"")
                    .replace("?profileFilter", "\"" + newData.getProfile() + "\"");
            executeUpdate(queryString);
        }
        else System.out.println("profile equal");

    }

}
