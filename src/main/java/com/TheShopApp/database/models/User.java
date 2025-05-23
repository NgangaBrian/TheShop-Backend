package com.TheShopApp.database.models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "users")
public class User {

    @Id
    private Long id;
    private String fullname;

    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    @Column(name = "googleId")
    private String googleId;
    @Column(name = "authType")
    private String authType;
    private Date created_at;
    @Transient
    private Date updated_at;

    public User() {}

    @JsonCreator
    public User(@JsonProperty("id") Long id){
        this.id = id;
    }

    public User(String fullname, String email, String password, String googleId, String authType) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.googleId = googleId;
        this.authType = authType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long userId) {
        this.id = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
