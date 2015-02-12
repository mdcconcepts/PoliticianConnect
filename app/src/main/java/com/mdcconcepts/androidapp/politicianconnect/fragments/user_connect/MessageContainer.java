package com.mdcconcepts.androidapp.politicianconnect.fragments.user_connect;

/**
 * {@link com.mdcconcepts.androidapp.politicianconnect.fragments.user_connect.MessageContainer}
 * Created by CODELORD on 2/10/2015.
 *
 * @author CODELORD
 */
public class MessageContainer {
    // TODO something remaining
    private int id;
    private String subject;
    private String date;

    public MessageContainer(int id, String subject, String date) {
        this.id = id;
        this.subject = subject;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
