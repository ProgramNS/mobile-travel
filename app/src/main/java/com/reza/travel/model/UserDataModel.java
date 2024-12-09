package com.reza.travel.model;


public class UserDataModel {

    private final String username,name,hp,password;

    public UserDataModel(String username,String password, String name, String hp){
        this.username = username;
        this.password = password;
        this.name = name;
        this.hp = hp;

    }

    public String getUsername(){return username;}
    public String getNameUser(){return name;}
    public String getHp(){return hp;}
    public String getPassword(){return password;}

}
