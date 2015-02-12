package com.mdcconcepts.androidapp.politicianconnect.politician;

/**
 * {@link com.mdcconcepts.androidapp.politicianconnect.politician.OperationContainer}
 * Created by CODELORD on 2/9/2015.
 *
 * @author CODELORD
 */
public class OperationContainer {

    private int id;
    private String place;
    private String time;
    private String title;
    private String desr;
    private String img;

    public OperationContainer(int id, String place, String time, String title, String desr, String img) {
        this.id = id;
        this.place = place;
        this.time = time;
        this.title = title;
        this.desr = desr;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesr() {
        return desr;
    }

    public void setDesr(String desr) {
        this.desr = desr;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
