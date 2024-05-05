package ru.nsu.fit.pupynin.webhelper.JenaWork.query_model;


import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;

import java.util.Iterator;
import java.util.function.Consumer;

public class QueryExecutor {

    private final Model model;

    public QueryExecutor(Model model) {
        this.model = model;
    }
    public void findStudent(String degree, String profile, Consumer<Iterator<QuerySolution>> consumer) {
        String queryText = new DataQueryAll(degree, profile).create();
        Query query = QueryFactory.create(queryText);
        try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
            consumer.accept(qe.execSelect());
        }
    }
    public void findStudent(String degree, String profile, String email, Consumer<Iterator<QuerySolution>> consumer) {
        String queryText = new DataQuery(degree, profile, email).create();
        Query query = QueryFactory.create(queryText);
        try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
            consumer.accept(qe.execSelect());
        }
    }
}
