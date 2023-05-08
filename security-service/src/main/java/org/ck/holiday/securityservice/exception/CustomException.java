package org.ck.holiday.securityservice.exception;

public class CustomException extends Exception {

    private String langField;
    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message , String langField) {
        super(message);
        this.langField = langField;
    }

    public String getLangField() {
        return langField;
    }

    public void setLangField(String langField) {
        this.langField = langField;
    }
}
