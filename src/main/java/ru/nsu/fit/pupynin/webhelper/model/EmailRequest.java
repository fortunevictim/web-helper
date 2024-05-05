package ru.nsu.fit.pupynin.webhelper.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

    private String from;
    private String to;
    private String subject;
    private String message;

}
