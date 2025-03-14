package org.example.dinger.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.dinger.dao.ProjectDao;
import org.example.dinger.dao.UserDao;
import org.example.dinger.dto.ProjectDto;
import org.example.dinger.entity.Project;
import org.example.dinger.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectDao projectDao;
    private final UserDao userDao;
    private final EntityManager entityManager;


    public ProjectService(ProjectDao projectDao, UserDao userDao, EntityManager entityManager) {
        this.projectDao = projectDao;
        this.userDao = userDao;
        this.entityManager = entityManager;
    }

    public Project createProject(ProjectDto projectDto, Integer userId) {

        User user = userDao.findById(userId)
                    .orElseThrow(EntityNotFoundException::new);
        Project project = new Project();
        project.setTitle(projectDto.getTitle());
        project.setDescription(projectDto.getDescription());
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        project.setUser(user);

        return projectDao.save(project);
    }

    public Project updateProject(Integer projectId, ProjectDto projectDto) {
        Project project = projectDao.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        project.setTitle(projectDto.getTitle());
        project.setDescription(projectDto.getDescription());
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        return projectDao.save(project);
    }

    public void deleteProject(Integer projectId) {
        Project project = projectDao.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        projectDao.delete(project);
    }

    public List<Project> getProjectsByUserId(Integer userId) {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return projectDao.findByUserId(userId);
    }

    public List<Project> getAllProjects() {
        return projectDao.findAll();

    }

    public List<Project> filterProjects(Integer userId, String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> project = cq.from(Project.class);

        Predicate predicate = cb.conjunction();

        if (title != null && !title.isEmpty()) {
            predicate = cb.and(predicate, cb.like(project.get("title"), "%" + title + "%"));
        }

        if (description != null && !description.isEmpty()) {
            predicate = cb.and(predicate, cb.like(project.get("description"), "%" + description + "%"));
        }

        if (createdAt != null) {
            predicate = cb.and(predicate, cb.like(project.get("createdAt"), "%" + createdAt + "%"));
        }

        if (updatedAt != null) {
            predicate = cb.and(predicate, cb.like(project.get("updatedAt"), "%" + updatedAt + "%"));
        }

        predicate = cb.and(predicate, cb.equal(project.get("user").get("id"), userId));

        cq.where(predicate);
        return entityManager.createQuery(cq).getResultList();
    }
}
