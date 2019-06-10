package project.web;

import java.security.Principal;
import java.util.Collection;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.domain.ProjectTask;
import project.services.ProjectTaskService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/backlog")
public class BacklogController {

    private final ProjectTaskService projectTaskService;

    @PostMapping(path = "/{projectId}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                                     @PathVariable String projectId,
                                                     Principal principal) {
       ProjectTask createdProjectTask = projectTaskService.addProjectTask(projectId, projectTask, principal.getName());
       return ResponseEntity.status(HttpStatus.CREATED).body(createdProjectTask);
    }

    @GetMapping(path = "/{projectId}")
    public ResponseEntity<Collection<ProjectTask>> getProjectBacklog(@PathVariable String projectId, Principal principal) {
        return ResponseEntity.ok(projectTaskService.findBacklogByProjectIdentifier(projectId, principal.getName()));
    }

    @GetMapping(path = "/{projectIdentifier}/{taskSequence}")
    public ResponseEntity<?> getProjectTask(@PathVariable String projectIdentifier,
                                            @PathVariable String taskSequence,
                                            Principal principal) {
        ProjectTask projectTask = projectTaskService.findProjectBySequence(projectIdentifier, taskSequence, principal.getName());
        return ResponseEntity.ok(projectTask);
    }

    @PutMapping(path = "/{projectIdentifier}/{taskSequence}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask,
                                                     @PathVariable String projectIdentifier,
                                                     @PathVariable String taskSequence,
                                                     Principal principal) {
        ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask, projectIdentifier, taskSequence, principal.getName());
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping(path = "/{projectIdentifier}/{taskSequence}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String projectIdentifier,
                                               @PathVariable String taskSequence,
                                                Principal principal) {
        projectTaskService.deleteProjectTask(projectIdentifier, taskSequence, principal.getName());
        return ResponseEntity.ok("ProjectTask " + projectIdentifier + "/" + taskSequence + " has been deleted");
    }
}

