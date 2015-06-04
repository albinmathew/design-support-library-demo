package com.albinmathew.samples.designsupportlibrary.models;

import android.graphics.Bitmap;

import java.util.List;

/**
 * @author albin
 * @date 3 /6/15
 */
public class Contact {

    private String id;
    private String firstName;
    private String lastName;
    private String displayName;
    private String phoneNumber;
    private String imageURI;
    private String email;

    public Contact(String id, String firstName, String lastName, String displayName,
                   String phoneNumber, String imageURI, String email,
                   Bitmap imageBitmap, List<String> contactNumbers, List<String> emailList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.phoneNumber = phoneNumber;
        this.imageURI = imageURI;
        this.email = email;
        this.imageBitmap = imageBitmap;
        this.contactNumbers = contactNumbers;
        this.emailList = emailList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private Bitmap imageBitmap;

    private List<String> contactNumbers;

    private List<String> emailList;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public List<String> getContactNumbers() {
        return contactNumbers;
    }

    public void setContactNumbers(List<String> contactNumbers) {
        this.contactNumbers = contactNumbers;
    }

    public List<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<String> emailList) {
        this.emailList = emailList;
    }
}
