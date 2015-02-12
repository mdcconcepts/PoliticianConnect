package com.mdcconcepts.androidapp.politicianconnect.fragments.services;

/**
 * {@link ServiceContainer}
 * Created by CODELORD on 2/6/2015.
 *
 * @author CODELORD
 */
public class ServiceContainer {

    private int service_category;
    private int service;
    private String contact;
    private String service_id;
    private String logo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public ServiceContainer(int service_category, int service, String contact, String service_id, String logo, String title) {
        this.service_category = service_category;
        this.service = service;
        this.contact = contact;
        this.service_id = service_id;
        this.logo = logo;
        this.title = title;
    }


    public int getService_category() {
        return service_category;
    }

    public void setService_category(int service_category) {
        this.service_category = service_category;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
