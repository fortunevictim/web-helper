package ru.nsu.fit.pupynin.webhelper.model;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentData {
    private String supervisor;
    private String coSupervisor;
    private String reviewer;
    private String consultant;
    private String practice_sup_NSU;
    private String practice_sup_org;
    private String studentFio;
    private String thesis;
    private String studentGroup;
    private String email;
    private String oldEmail; //Нужен для того, чтобы обновить email на новый
    private String practiceOrder;
    private String thesisOrder;
    private String internshipPlaceShort;
    private String internshipPlaceFull;
    private String internshipPlace;
    private String profile;
}

