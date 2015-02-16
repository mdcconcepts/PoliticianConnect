package com.mdcconcepts.androidapp.politicianconnect.politician;

/**
 * {@link com.mdcconcepts.androidapp.politicianconnect.politician.GCMMessageContainer}
 * Created by CODELORD on 2/14/2015.
 *
 * @author CODELORD
 */
public class GCMMessageContainer {
    private int id;
    private String media_type;
    private String media_url;
    private String message;
    private String title;
    private String time;

    public GCMMessageContainer(int id, String media_type, String media_url, String message, String title, String time) {
        this.id = id;
        this.media_type = media_type;
        this.media_url = media_url;
        this.message = message;
        this.title = title;
        this.time = time;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
