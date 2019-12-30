package com.example.studentify_android.model.form;

public class NewPassword {
    private String eMail;
    private String currentPswd;
    private String newPswd;

    public NewPassword(String eMail, String currentPswd, String newPswd) {
        this.eMail = eMail;
        this.currentPswd = currentPswd;
        this.newPswd = newPswd;
    }

    public NewPassword() {
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getCurrentPswd() {
        return currentPswd;
    }

    public void setCurrentPswd(String currentPswd) {
        this.currentPswd = currentPswd;
    }

    public String getNewPswd() {
        return newPswd;
    }

    public void setNewPswd(String newPswd) {
        this.newPswd = newPswd;
    }
}
