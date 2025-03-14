package org.example.dinger.util;


import org.example.dinger.dto.ProjectDto;
import org.example.dinger.entity.Project;
import org.springframework.beans.BeanUtils;

public class ProjectUtil {
    public static ProjectDto toDto(Project project) {

        ProjectDto projectDto = new ProjectDto();
        BeanUtils.copyProperties(project, projectDto);
        return projectDto;
    }

    public static Project toEntity(ProjectDto projectDto) {
        Project project = new Project();
        BeanUtils.copyProperties(projectDto, project);
        return project;
    }

}
