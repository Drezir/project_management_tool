package project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.domain.Backlog;
import project.domain.Project;
import project.exceptions.ServerError;
import project.exceptions.ServerException;
import project.repositories.BacklogRepository;
import project.repositories.ProjectRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final BacklogRepository backlogRepository;

    public Project saveOrUpdate(Project project) {
        project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
        if (project.getId() == null) {
            project.setBacklog(new Backlog());
            project.getBacklog().setProject(project);
            project.getBacklog().setProjectIdentifier(project.getProjectIdentifier());
        } else {
            project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier()));
        }

        try {
            return projectRepository.save(project);
        } catch (Exception ex) {
            throw new ServerException("Cannot save or update project", ex);
        }
    }

    public Project findByProjectIdentifier(String projectIdentifier) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if (project == null) {
            throw new ServerException("Cannot find project", null)
                    .withError(ServerError.PROJECT_DOES_NOT_EXIST, "projectIdentifier", projectIdentifier);
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
