package com.corporateapp.models;

/**
 * Created by Ashwini on 16/10/2017.
 */

public class ModelEmergencyContacts {

    String Emergency_contact_name, Emergency_contact_number, address, tag;
    int emergency_contact_img;

    public String getEmergency_contact_name() {
        return Emergency_contact_name;
    }

    public void setEmergency_contact_name(String emergency_contact_name) {
        Emergency_contact_name = emergency_contact_name;
    }

    public String getEmergency_contact_number() {
        return Emergency_contact_number;
    }

    public void setEmergency_contact_number(String emergency_contact_number) {
        Emergency_contact_number = emergency_contact_number;
    }

    public int getEmergency_contact_img() {
        return emergency_contact_img;
    }

    public void setEmergency_contact_img(int emergency_contact_img) {
        this.emergency_contact_img = emergency_contact_img;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
