package com.example.socialbasejava.model;

public class UserModel {
    public String name,uid,email;
    public UserModel(){
    }
    public UserModel(String name , String uid,String email){
        this.email = email;
        this.name = name;
        this.uid = uid;
    }

}
