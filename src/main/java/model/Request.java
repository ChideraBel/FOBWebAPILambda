package model;
public class Request {
    private String inputField;

    // Default constructor
    public Request() {
    }

    public Request(String input) {
        inputField = input;
    }

    public String getInputField() {
        return inputField;
    }

    public void setInputField(String inputField) {
        this.inputField = inputField;
    }
}