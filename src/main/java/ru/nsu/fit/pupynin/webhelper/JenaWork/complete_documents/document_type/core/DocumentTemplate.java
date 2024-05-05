package ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.core;

import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.ReplacementCreator;

import java.util.Map;

public interface DocumentTemplate {

    /**
     * template field -> replacer
     */
    Map<String, ReplacementCreator> replacements();

    /**
     * Original template file name.
     */
    String fileName();

    /**
     * Solution var name that will be used as prefix for generated document
     */
    default String prefixVarName() {
        return "фио_студента";
    }

    default boolean generateFor(Map<String, String> studentReplacements) {
        return true;
    }
}

