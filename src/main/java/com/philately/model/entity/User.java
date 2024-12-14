package com.philately.model.entity;

import jakarta.persistence.*;


import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Stamp> addedStamps;

    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Stamp> wishedStamps;

    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Stamp> purchasedStamps;


    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Stamp> getAddedStamps() {
        return addedStamps;
    }

    public User setAddedStamps(Set<Stamp> addedStamps) {
        this.addedStamps = addedStamps;
        return this;
    }

    public Set<Stamp> getWishedStamps() {
        return wishedStamps;
    }

    public User setWishedStamps(Set<Stamp> wishedStamps) {
        this.wishedStamps = wishedStamps;
        return this;
    }

    public Set<Stamp> getPurchasedStamps() {
        return purchasedStamps;
    }

    public User setPurchasedStamps(Set<Stamp> boughtStamps) {
        this.purchasedStamps = boughtStamps;
        return this;

    }
}
