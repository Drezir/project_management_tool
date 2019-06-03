package project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.domain.Project;
import project.exceptions.ProjectIdException;
import project.repositories.ProjectRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project saveOrUpdate(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception ex) {
            throw new ProjectIdException("Project ID already exists", project.getProjectIdentifier());
        }
    }

    public Project findByProjectIdentifier(String projectIdentifier) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Project ID does not exist", projectIdentifier);
        }
        return project;
    }

    public Iterable<Project> listAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = findByProjectIdentifier(projectId);
        projectRepository.delete(project);
    }
}
