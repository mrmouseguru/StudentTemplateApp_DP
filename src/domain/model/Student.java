package domain.model;

import java.io.Serializable;

public class Student implements Serializable{
    private int id;
    private String name;
    private String major;
    private int javaMark;
    private int htmlMark;
    private int cssMark;

    public Student(int id, String name, String major, int javaMark, int htmlMark, int cssMark) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.javaMark = javaMark;
        this.htmlMark = htmlMark;
        this.cssMark = cssMark;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getJavaMark() {
        return javaMark;
    }

    public void setJavaMark(int javaMark) {
        this.javaMark = javaMark;
    }

    public int getHtmlMark() {
        return htmlMark;
    }

    public void setHtmlMark(int htmlMark) {
        this.htmlMark = htmlMark;
    }

    public int getCssMark() {
        return cssMark;
    }

    public void setCssMark(int cssMark) {
        this.cssMark = cssMark;
    }

    // Business logic to compute average mark
    public double computeAverageMark() {
        return (2.0 * javaMark + htmlMark + cssMark) / 4.0;
    }
}
