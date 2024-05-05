package ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents;

import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.core.DocumentTemplate;

import java.util.function.Predicate;

@FunctionalInterface
public interface DocumentTemplateFilter extends Predicate<DocumentTemplate> {
    DocumentTemplateFilter EMPTY = d -> true;
}
