package com.phan.thang.stranger;

/**
 * Created by thang on 26.08.2016.
 */
public class LoveNote {
    public String city;
    public String username;
    public String message;
    public String age;
    public String dateSent;
    public String uid;

    public LoveNote(){

    }
    public LoveNote(String city, String username, String age, String message, String dateSent, String uid){
        this.city = city;
        this.username = username;
        this.age = age;
        this.message = message;
        this.dateSent = dateSent;
        this.uid = uid;
    }

    public String toString(){
        return "City: " + city + "\n Username : " + username + "\n Message: " + message + "\n Age: " + age + "\n Sent: " + dateSent + "\n UID: " + uid;
    }
}
