package nl.bos.entity.eisconnector.helloworld.dao;

import java.io.Serializable;

public class Project implements Serializable {
    private int id;
    private String subject;

    public Project(int id, String subject) {
        this.id = id;
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
