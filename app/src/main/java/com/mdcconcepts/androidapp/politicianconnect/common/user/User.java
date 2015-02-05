package com.mdcconcepts.androidapp.politicianconnect.common.user;

/**
 * <h>{@link com.mdcconcepts.androidapp.politicianconnect.common.user.User}</h>
 * <p>This class is used for storing User data</p>
 * Created by CODELORD on 2/3/2015.
 *
 * @author CODELORD
 */
public class User {
    private int user_id;
    private String name;
    private int gender;
    private String mobile_no;
    private String birth_date;
    private int state;
    private int city;

    public User(int user_id, String name, int gender, String mobile_no, String birth_date, int state, int city) {
        this.user_id = user_id;
        this.name = name;
        this.gender = gender;
        this.mobile_no = mobile_no;
        this.birth_date = birth_date;
        this.state = state;
        this.city = city;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }
}
