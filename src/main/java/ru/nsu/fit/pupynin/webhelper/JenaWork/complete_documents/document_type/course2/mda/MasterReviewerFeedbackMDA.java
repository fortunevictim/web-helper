package ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.course2.mda;

import com.github.petrovich4j.Case;
import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.ReplacementCreator;
import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.core.ReviewerFeedbackMDA;

import java.util.Map;

import static ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.ReplacementCreator.*;

public record MasterReviewerFeedbackMDA() implements ReviewerFeedbackMDA {

    private static final Map<String, ReplacementCreator> DOC_FIELD_TO_SOLUTION = Map.of(
            "имяСтудентаР", fullName("фио_студента", Case.Genitive),
            "имяСтудентаИ", simple("фио_студента"),
            "имяРуководителяВКР", simple("фио_руководителя"),
            "темаВКР", simple("тема_вкр"),
            "фиоРецензента", simple("фио_рецензента"),
            "должностьРецензента", simple("должность_рецензента"),
            "формаСтудента", genderForm("фио_студента")
    );

    @Override
    public Map<String, ReplacementCreator> replacements() {
        return DOC_FIELD_TO_SOLUTION;
    }

    @Override
    public String fileName() {
        return "09.04.01_KMiAD_VKR_recenziya.docx";
    }
}
