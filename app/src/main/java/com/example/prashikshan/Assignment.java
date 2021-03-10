package com.example.prashikshan;

public class Assignment
{
    private String assignmentTitle;
    private String problemStatement;
    private String class_name;
    private String institution;
    private String subject;
    private boolean active;

    public Assignment()
    {
        /*
         * Default Constructor
         */
    }

    public Assignment(String assignmentTitle, String problemStatement, String class_name, String institution, String subject)
    {
        this.assignmentTitle = assignmentTitle;
        this.problemStatement = problemStatement;
        this.class_name = class_name;
        this.institution = institution;
        this.subject = subject;
        this.active = true;
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

    public String getSubject() {
        return subject;
    }

    public boolean isActive()
    {
        return(active == true);
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }
}
