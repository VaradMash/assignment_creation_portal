package com.example.prashikshan;

public class Assignment
{
    private String assignmentTitle;
    private String problemStatement;
    private String class_name;
    private String institution;

    public Assignment()
    {
        /*
         * Default Constructor
         */
    }

    public Assignment(String assignmentTitle, String problemStatement, String class_name, String institution)
    {
        this.assignmentTitle = assignmentTitle;
        this.problemStatement = problemStatement;
        this.class_name = class_name;
        this.institution = institution;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public String getClass_name() {
        return class_name;
    }

    public String getInstitution() {
        return institution;
    }
}