package ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.course4;

import com.github.petrovich4j.Case;
import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.ReplacementCreator;
import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.core.SupervisorFeedback;

import java.util.Map;

import static ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.ReplacementCreator.fullName;
import static ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.ReplacementCreator.simple;

public record BachelorSupervisorFeedback() implements SupervisorFeedback {
    private static final Map<String, ReplacementCreator> DOC_FIELD_TO_SOLUTION = Map.of(
            "имяСтудентаР", fullName("фио_студента", Case.Genitive),
            "имяСтудентаИ", simple("фио_студента"),
            "имяРуководителяВКР", simple("фио_руководителя"),
            "УчСтепРукВКР", simple("ученая_степень_руководителя_ВКР"),
            "должностьРуководителяВКР", simple("должность_руководителя_вкр"),
            "фиоСоруководителяВКР", simple("фио_соруководителя_вкр"),
            "темаВКР", simple("тема_вкр"),
            "должностьСоруководителяВКР", simple("должность_соруководителя_вкр")
    );

    @Override
    public Map<String, ReplacementCreator> replacements() {
        return DOC_FIELD_TO_SOLUTION;
    }

    @Override
    public String fileName() {
        return "09.03.01_PIiKN_VKR_otzyv.docx";
    }

    @Override
    public boolean generateFor(Map<String, String> studentReplacements) {
        return (!studentReplacements.containsKey("фиоСоруководителяВКР") && !studentReplacements.containsKey("должностьСоруководителяВКР"));
    }
}
