package ru.nsu.fit.pupynin.webhelper.JenaWork;

import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.core.DocumentTemplate;
import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.core.*;
import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.course2.mda.*;
import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.course2.trps.*;
import ru.nsu.fit.pupynin.webhelper.JenaWork.complete_documents.document_type.course4.*;

sealed public interface Degree {

    Degree[] DEGREES = new Degree[]{new Bachelor(), new MasterMDA(), new MasterTPRS()};

    static Degree[] all() {
        return DEGREES;
    }

    String name();

    String dir();

    String profile();

    DocumentTemplate[] toGenerate();
}


final class Bachelor implements Degree {

    /**
     * Degree name, used inside query
     */
    @Override
    public String name() {
        return "Бакалавриат";
    }

    /**
     * Subdirectory where to search for tempates / where to dump generated docs
     */
    @Override
    public String dir() {
        return "bachelors/4th_course";
    }

    @Override
    public String profile() {
        return "Программная инженерия и компьютерные науки";
    }

    /**
     * Which templates should be generated
     */
    @Override
    public DocumentTemplate[] toGenerate() {
        return new DocumentTemplate[]{new BachelorSupervisorFeedback(),
                new BachelorReviewerFeedback(), new BachelorIndividualTask(),
                new BachelorPracticeReport(), new BachelorPracticeFeedback(), new BachelorSupervisorFeedback2(), new BachelorApplicationForPractice()};
    }
}

final class MasterMDA implements Degree {

    /**
     * Degree name, used inside query
     */
    @Override
    public String name() {
        return "Магистратура";
    }

    /**
     * Subdirectory where to search for tempates / where to dump generated docs
     */
    @Override
    public String dir() {
        return "masters/2nd_course/mda";
    }

    @Override
    public String profile() {
        return "Компьютерное моделирование и анализ данных";
    }

    /**
     * Which templates should be generated
     */
    @Override
    public DocumentTemplate[] toGenerate() {


        return new DocumentTemplate[]{
                new MasterSupervisorFeedbackMDA(), new MasterSupervisorFeedbackMDA2(),
                new MasterReviewerFeedbackMDA(), new MasterIndividualTaskMDA(),
                new MasterPracticeReportMDA(), new MasterPracticeFeedbackMDA(), new MasterApplicationForPracticeMDA()};
    }
}

final class MasterTPRS implements Degree {

    @Override
    public String name() {
        return "Магистратура";
    }

    @Override
    public String dir() {
        return "masters/2nd_course/tprs";
    }

    @Override
    public String profile() {
        return "Технология разработки программных систем";
    }

    @Override
    public DocumentTemplate[] toGenerate() {
        return new DocumentTemplate[]{
                new MasterSupervisorFeedbackTRPS(),
                new MasterReviewerFeedbackTRPS(), new MasterIndividualTaskTRPS(),
                new MasterPracticeReportTRPS(), new MasterPracticeFeedbackTRPS(),
                new MasterSupervisorFeedbackTRPS2(), new MasterApplicationForPracticeTRPS()};
    }
}