package org.example.dinger.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String userName;
    @Column(unique = true, nullable = false)
    private String phonenumber;
    @Column(unique = true, nullable = false)
    private String nrc;
    private String password;
    private boolean approved;
    private boolean blocked;

    public User() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Role> roles =
            new HashSet<Role>();

    public void addRole(Role role) {
        roles.add(role);
    }

    public User(String userName, String phonenumber, String nrc, String password, boolean approved, boolean blocked) {
        this.userName = userName;
        this.phonenumber = phonenumber;
        this.nrc = nrc;
        this.password = password;
        this.approved = approved;
        this.blocked = blocked;
    }
}
