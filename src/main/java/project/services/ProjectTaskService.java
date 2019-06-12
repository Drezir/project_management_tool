package project.services;

import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;
import project.domain.Backlog;
import project.domain.ProjectTask;
import project.exceptions.ServerError;
import project.exceptions.ServerException;
import project.repositories.ProjectTaskRepository;
import project.utils.NullAwareBeanUtilsBean;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectTaskService {

    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
        Backlog backlog = projectService.findByProjectIdentifier(projectIdentifier, username).getBacklog();
        Integer sequence = backlog.getPTSequence();
        projectTask.setBacklog(backlog);
        projectTask.setProjectSequence(projectIdentifier + "-" +  sequence);
        projectTask.setProjectIdentifier(projectIdentifier);
        if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
            projectTask.setPriority(3);
        }
        if (projectTask.getStatus() == null || projectTask.getStatus().isEmpty()) {
            projectTask.setStatus("TODO");
        }
        backlog.setPTSequence(sequence + 1);
        return projectTaskRepository.save(projectTask);
    }

    private Backlog checkBacklogExistence(String projectIdentifier, String username) {
        return projectService.findByProjectIdentifier(projectIdentifier, username).getBacklog();
    }
    private ProjectTask findProjectTask(String projectIdentifier, String taskSequence, String username) {
        checkBacklogExistence(projectIdentifier, username);
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(taskSequence);
        if (projectTask == null) {
            throw new ServerException("Cannot save or update project", null)
                    .withError(ServerError.PROJECT_DOES_NOT_EXIST, "projectIdentifier", projectIdentifier);
        }
        if (!projectTask.getProjectIdentifier().equals(projectIdentifier)) {
            throw new ServerException("Cannot save or update project", null)
                    .withError(ServerError.PROJECT_DOES_NOT_CONTAIN_TASK, "projectIdentifier", projectIdentifier)
                    .withError(ServerError.TASK_DOES_NOT_BELONG_TO_PROJECT, "projectTask.projectIdentifier", projectTask.getProjectIdentifier());
        }
        return projectTask;
    }

    public ProjectTask findProjectBySequence(String projectIdentifier, String taskSequence, String username) {
        return findProjectTask(projectIdentifier, taskSequence, username);
    }

    public Collection<ProjectTask> findBacklogByProjectIdentifier(String projectIdentifier, String username) {
        checkBacklogExistence(projectIdentifier, username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier);
    }

    public ProjectTask updateByProjectSequence(ProjectTask projectTask, String projectIdentifier, String projectSequence, String username) {

        ProjectTask target = findProjectTask(projectIdentifier, projectSequence, username);
        BeanUtilsBean ignoreNull = new NullAwareBeanUtilsBean();
        try {
            ignoreNull.copyProperties(target, projectTask);
            return projectTaskRepository.save(target);

        } catch (IllegalAccessException | IllegalArgumentException ex) {
            log.error("Error copying properties using beanutils", ex);
            throw new ServerException("Error copying properties using beanutils", ex)
                .withError(ServerError.PROJECT_CANNOT_PERSIST("Error copying properties using beanutils"), "newProjectTask", projectTask);
        } catch (Exception ex) {
            log.error("Error updating project task", ex);
            throw new ServerException("Error when updating project task", ex)
                .withError(ServerError.PROJECT_CANNOT_PERSIST("Cannot update project task"), "newProjectTask", target);
        }
    }

    public void deleteProjectTask(String projectIdentifier, String taskSequence, String username) {
        ProjectTask projectTask = findProjectTask(projectIdentifier, taskSequence, username);
        projectTaskRepository.delete(projectTask);
    }
}
