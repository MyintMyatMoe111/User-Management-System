package org.example.dinger.dao;

import org.example.dinger.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectDao extends JpaRepository<Project, Integer> {

    List<Project> findByUserId(Integer userId);
}
