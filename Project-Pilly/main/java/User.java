package com.example.pilly;

public class User {
    private String userid;
    private String username;
    private String birthday;
    private String email;
    private String gender;

    public User() {}

    public User(String userid, String username, String birthday, String email, String gender) {
        this.userid = userid;
        this.username = username;
        this.birthday = birthday;
        this.email = email;
        this.gender = gender;
    }

    public String getUserid() { return userid; }
    public void setUserid(String userid) { this.userid = userid; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}
