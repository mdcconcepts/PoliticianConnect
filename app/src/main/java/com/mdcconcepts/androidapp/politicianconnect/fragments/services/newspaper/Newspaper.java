package com.mdcconcepts.androidapp.politicianconnect.fragments.services.newspaper;

/**
 * {@link Newspaper}
 * <p>This class is used to hold Newspaper Logo and Website link</p>
 * Created by CODELORD on 2/6/2015.
 *
 * @author CODELORD
 */
public class Newspaper {
    private String newspaper_logo;
    private String Newspaper_website;

    public Newspaper(String newspaper_logo, String newspaper_website) {
        this.newspaper_logo = newspaper_logo;
        Newspaper_website = newspaper_website;
    }

    public String getNewspaper_logo() {
        return newspaper_logo;
    }

    public void setNewspaper_logo(String newspaper_logo) {
        this.newspaper_logo = newspaper_logo;
    }

    public String getNewspaper_website() {
        return Newspaper_website;
    }

    public void setNewspaper_website(String newspaper_website) {
        Newspaper_website = newspaper_website;
    }
}
