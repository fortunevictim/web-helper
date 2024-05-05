package ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.course2.trps;

import com.github.petrovich4j.Case;
import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.ReplacementCreator;
import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.core.SupervisorFeedbackTRPS;
import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.core.SupervisorFeedbackTRPS2;

import java.util.Map;

import static ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.ReplacementCreator.fullName;
import static ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.ReplacementCreator.simple;

public record MasterSupervisorFeedbackTRPS2() implements SupervisorFeedbackTRPS2 {
    private static final Map<String, ReplacementCreator> DOC_FIELD_TO_SOLUTION = Map.of(
            "имяСтудентаР", fullName("фио_студента", Case.Genitive),
            "имяСтудентаИ", simple("фио_студента"),
            "имяРуководителяВКР", simple("фио_руководителя"),
            "темаВКР", simple("тема_вкр"),
            "УчСтепРукВКР", simple("ученая_степень_руководителя_ВКР"),
            "должностьРуководителяВКР", simple("должность_руководителя_вкр"),
            "фиоСоруководителяВКР", simple("фио_соруководителя_вкр"),
            "должностьСоруководителяВКР", simple("должность_соруководителя_вкр")
    );

    @Override
    public Map<String, ReplacementCreator> replacements() {
        return DOC_FIELD_TO_SOLUTION;
    }

    @Override
    public String fileName() {
        return "09.04.01_TRPS_VKR_otzyv_2.docx";
    }

    @Override
    public boolean generateFor(Map<String, String> studentReplacements) {
        return (studentReplacements.containsKey("фиоСоруководителяВКР") && studentReplacements.containsKey("должностьСоруководителяВКР"));
    }
}
