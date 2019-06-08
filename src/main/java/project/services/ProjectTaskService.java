package project.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;
import project.domain.Backlog;
import project.domain.ProjectTask;
import project.exceptions.ServerException;
import project.repositories.BacklogRepository;
import project.repositories.ProjectTaskRepository;
import project.utils.NullAwareBeanUtilsBean;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectTaskService {

    private final ProjectTaskRepository projectTaskRepository;
    private final BacklogRepository backlogRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        if (backlog == null) {
            throw new ServerException("Cannot save or update project", null)
                    .awareObject("Project identifier does not exist", projectIdentifier);
        }
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

    private ProjectTask findProjectTak(String projectIdentifier, String taskSequence) {
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        if (backlog == null) {
            throw new ServerException("Cannot save or update project", null)
                    .awareObject("Project identifier does not exist", projectIdentifier);
        }

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(taskSequence);
        if (projectTask == null) {
            throw new ServerException("Cannot save or update project", null)
                    .awareObject("Project sequence does not exist", taskSequence);
        }
        if (!projectTask.getProjectIdentifier().equals(projectIdentifier)) {
            throw new ServerException("Cannot save or update project", null)
                    .awareObject("Project task does not belong to project.", projectIdentifier)
                    .awareObject("Project does not contain given project task", projectTask.getProjectIdentifier());
        }
        return projectTask;
    }

    public ProjectTask findProjectBySequence(String projectIdentifier, String taskSequence) {
        return findProjectTak(projectIdentifier, taskSequence);
    }

    public Collection<ProjectTask> findBacklogByProjectIdentifier(String projectIdentifier) {
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier);
    }

    public ProjectTask updateByProjectSequence(ProjectTask projectTask, String projectIdentifier, String projectSequence) {

        ProjectTask target = findProjectTak(projectIdentifier, projectSequence);
        BeanUtilsBean ignoreNull = new NullAwareBeanUtilsBean();
        try {
            ignoreNull.copyProperties(target, projectTask);
            return projectTaskRepository.save(target);
        } catch (Exception ex) {
            log.error("Error copying properties using beanutils", ex);
            throw new ServerException("Error copying properties using beanutils", ex)
                    .awareObject("New project task", projectSequence)
                    .awareObject("Project task to update", target);
        }
    }

    public void deleteProjectTask(String projectIdentifier, String taskSequence) {
        ProjectTask projectTask = findProjectTak(projectIdentifier, taskSequence);
        projectTaskRepository.delete(projectTask);
    }
}
