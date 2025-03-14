package org.example.dinger.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String roleName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;
        return Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
