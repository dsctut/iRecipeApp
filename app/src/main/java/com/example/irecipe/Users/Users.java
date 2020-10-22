package com.example.irecipe.Users;

public class Users
{
    private String Username,Phone_Number,Email_Address,Password,Image;

    public Users()
    {

    }

    public Users(String username, String phone_Number, String email_Address, String password, String image) {
        Username = username;
        Phone_Number = phone_Number;
        Email_Address = email_Address;
        Password = password;
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

    public String getEmail_Address() {
        return Email_Address;
    }

    public void setEmail_Address(String email_Address) {
        Email_Address = email_Address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}

