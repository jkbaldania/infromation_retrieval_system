package com.v1.irs.user;

import com.v1.irs.batch.Batch;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    public User() {}

    public User(String username, String email, String name, String password) {

//        this.userId = userId;
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
    }

//    @OneToMany(mappedBy="user")
//    private Set<Batch> batches;

//    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
//    @Column(name="user_id")
//    private Integer userId;

    @Id
    @Column(name="user_name")
    private String username;

    @Column(name="email")
    private String email;

    @Column(name="name")
    private String name;

    @Column(name="password")
    private String password;

//    public Integer getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}