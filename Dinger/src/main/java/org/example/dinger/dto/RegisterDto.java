package org.example.dinger.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;


public class RegisterDto {
    private String userName;
    private String phonenumber;
    private String nrc;
    private String password;
    private boolean approved;
    private boolean blocked;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
