package com.mdcconcepts.androidapp.politicianconnect.fragments.services.shopping;

/**
 * {@link ShoppingContainer}
 * Created by CODELORD on 2/7/2015.
 *
 * @author CODELORD
 */
public class ShoppingContainer {
    //TODO Create documentation after finishing service.
    private int id;
    private String title;
    private String logo;

    public ShoppingContainer(int id, String title, String logo) {
        this.id = id;
        this.title = title;
        this.logo = logo;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
