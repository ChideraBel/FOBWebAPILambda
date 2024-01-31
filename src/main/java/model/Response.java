package model;

public class Response {
    private String outputField;

    public Response(String output){
        outputField = output;
    }
    public String getOutputField() {
        return outputField;
    }

    public void setOutputField(String outputField) {
        this.outputField = outputField;
    }
}
