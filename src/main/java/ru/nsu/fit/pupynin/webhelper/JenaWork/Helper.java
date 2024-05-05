package ru.nsu.fit.pupynin.webhelper.JenaWork;


import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.DocumentGenerator;
import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.DocumentTemplateFilter;
import ru.nsu.fit.pupynin.webhelper.JenaWork.query_model.QueryExecutor;


import java.io.IOException;
import java.io.InputStream;

public class Helper {
    //TODO - that's how it was
       //  private static final String MODEL_FILENAME = "/main_students_all.owl";
    private static final String MODEL_FILENAME = "D:\\main_students_all.owl";
    private static final Degree[] DEGREES = Degree.all();

    public static void main(String[] args) throws IOException {
        //generateTemplates(DocumentTemplateFilter.EMPTY);
        generateTemplates(DocumentTemplateFilter.EMPTY, "a.shabalin4@g.nsu.ru");
    }

    public static void generateTemplates(DocumentTemplateFilter documentTemplateFilter) throws IOException {
        Model model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
        try (InputStream in = FileManager.get().open(MODEL_FILENAME)) {
            //try (InputStream in = Helper.class.getResourceAsStream(MODEL_FILENAME)) {
            model.read(in, "RDF/XML");
        }
        System.out.println("Helper: generateTemplate: model has read");
        QueryExecutor executor = new QueryExecutor(model);
        for (Degree degree : DEGREES) {
            executor.findStudent(degree.name(), degree.profile(),
                    solution -> DocumentGenerator.generate(degree, solution, documentTemplateFilter));
        }
        System.out.println("Helper: generateTemplate: Degree iterator has ended");
    }

    public static void generateTemplates(DocumentTemplateFilter documentTemplateFilter, String email) throws IOException {
        Model model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, null);
        try (InputStream in = FileManager.get().open(MODEL_FILENAME)) {
        //try (InputStream in = Helper.class.getResourceAsStream(MODEL_FILENAME)) {
            model.read(in, "RDF/XML");
        }
        System.out.println("Helper: generateTemplate(email): model has read");
        QueryExecutor executor = new QueryExecutor(model);
        for (Degree degree : DEGREES) {
            executor.findStudent(degree.name(), degree.profile(), email,
                    solution -> DocumentGenerator.generate(degree, solution, documentTemplateFilter));
        }
        System.out.println("Helper: generateTemplate(string): Degree iterator has ended");
    }
}