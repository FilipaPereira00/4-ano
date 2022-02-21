package com.bosques.rasbet_backend.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;


@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String gender;
    private String email;
    private String phone;
    private String nat;

    public User() {

    }

    public User(String gender, String email, String phone, String nat) {
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.nat = nat;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    @Override
    public String toString() {
        return "User{" +
                "gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", nat='" + nat + '\'' +
                '}';
    }
}
