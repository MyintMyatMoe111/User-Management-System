package org.example.dinger.conntroller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.example.dinger.dto.ProjectDto;
import org.example.dinger.entity.Project;
import org.example.dinger.entity.User;
import org.example.dinger.service.AuthService;
import org.example.dinger.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Project> createProject(@PathVariable Integer userId, @RequestBody ProjectDto projectDto) {
        Project project = projectService.createProject(projectDto, userId);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/all")
    public List<Project> getAllProjects(){
        return projectService.getAllProjects();
    }

    @PutMapping("/update/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer projectId, @RequestBody ProjectDto projectDto) {
        Project updatedProject = projectService.updateProject(projectId, projectDto);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable Integer projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.ok("Project deleted successfully");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Project>> getProjectsByUserId(@PathVariable Integer userId) {
        List<Project> projects = projectService.getProjectsByUserId(userId);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/filter/{userId}")
    public ResponseEntity<List<Project>> filterProjects(
            @PathVariable Integer userId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) LocalDateTime createdAt,
            @RequestParam(required = false) LocalDateTime updatedAt) {

        List<Project> projects = projectService.filterProjects(userId, title, description, createdAt, updatedAt);
        return ResponseEntity.ok(projects);
    }

}
