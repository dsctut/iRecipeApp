package com.example.irecipe.Model;

public class User {

    private String Username, Phone_Number, Password, Email_Address, Image;

    public User() {

    }

    public User(String username, String phone_Number, String password, String email_Address, String image) {
        Username = username;
        Phone_Number = phone_Number;
        Password = password;
        Email_Address = email_Address;
        Image = image;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail_Address() {
        return Email_Address;
    }

    public void setEmail_Address(String email_Address) {
        Email_Address = email_Address;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}