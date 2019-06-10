package project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.domain.Backlog;
import project.domain.Project;
import project.domain.User;
import project.exceptions.ServerError;
import project.exceptions.ServerException;
import project.repositories.BacklogRepository;
import project.repositories.ProjectRepository;
import project.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final BacklogRepository backlogRepository;
    private final UserRepository userRepository;

    public Project saveOrUpdate(Project project, String username) {
        project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
        project.setUser(findUser(username));
        project.setProjectLeader(username);
        if (project.getId() == null) {
            // create part
            project.setBacklog(new Backlog());
            project.getBacklog().setProject(project);
            project.getBacklog().setProjectIdentifier(project.getProjectIdentifier());
        } else {
            // update part
            Project foundProject = findByProjectIdentifier(project.getProjectIdentifier(), username);
            if (foundProject != null && !foundProject.getUser().getUsername().equals(username)) {
                throw new ServerException("User cannot access project of different user", null)
                    .withError(ServerError.PROJECT_AUTHENTICATION, "projectIdentifier", project.getProjectIdentifier())
                    .withError(ServerError.PROJECT_AUTHENTICATION, "username", username);
            }
            project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier()));
        }

        try {
            return projectRepository.save(project);
        } catch (Exception ex) {
            throw new ServerException("Cannot save or update project", ex)
                .withError(ServerError.PROJECT_CANNOT_PERSIST, "id", project.getId())
                .withError(ServerError.PROJECT_CANNOT_PERSIST, "projectIdentifier", project.getProjectIdentifier());
        }
    }

    public User findUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ServerException("User was not found", null)
                .withError(ServerError.USER_DOES_NOT_EXIST, "username", username);
        }
        return user;
    }

    public Project findByProjectIdentifier(String projectIdentifier, String username) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if (project == null) {
            throw new ServerException("Cannot find project", null)
                    .withError(ServerError.PROJECT_DOES_NOT_EXIST, "projectIdentifier", projectIdentifier);
        }

        if (! project.getProjectLeader().equals(username)) {
            throw new ServerException("User cannot access project of different user", null)
                .withError(ServerError.PROJECT_AUTHENTICATION, "projectIdentifier", projectIdentifier)
                .withError(ServerError.PROJECT_AUTHENTICATION, "username", username);
        }

        return project;
    }

    public Iterable<Project> listAllProjects(String username) {
        return projectRepository.findByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username) {
        Project project = findByProjectIdentifier(projectId, username);
        projectRepository.delete(project);
    }
}
