package ru.nsu.fit.pupynin.webhelper.model;

public class UserChangePasswordRequest {

    private String newPassword;

    private String confirmPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
