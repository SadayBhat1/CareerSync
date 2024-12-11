package com.finalproj.kleplacementapp;

public class uploadPDF {
    public String name;
    public String url;

    public uploadPDF() {
        // Default constructor required for Firebase deserialization
    }

    public uploadPDF(String name, String url)
    {
        this.name=name;
        this.url=url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
