package project.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.domain.ProjectTask;
import project.services.ProjectTaskService;

import javax.validation.Valid;
import java.util.Collection;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/backlog")
public class BacklogController {

    private final ProjectTaskService projectTaskService;

    @PostMapping(path = "/{projectId}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                                     @PathVariable String projectId) {
       ProjectTask createdProjectTask = projectTaskService.addProjectTask(projectId, projectTask);
       return ResponseEntity.status(HttpStatus.CREATED).body(createdProjectTask);
    }

    @GetMapping(path = "/{projectId}")
    public ResponseEntity<Collection<ProjectTask>> getProjectBacklog(@PathVariable String projectId) {
        return ResponseEntity.ok(projectTaskService.findBacklogByProjectIdentifier(projectId));
    }

    @GetMapping(path = "/{projectIdentifier}/{taskSequence}")
    public ResponseEntity<?> getProjectTask(@PathVariable String projectIdentifier,
                                            @PathVariable String taskSequence) {
        ProjectTask projectTask = projectTaskService.findProjectBySequence(projectIdentifier, taskSequence);
        return ResponseEntity.ok(projectTask);
    }

    @PutMapping(path = "/{projectIdentifier}/{taskSequence}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask,
                                                     @PathVariable String projectIdentifier,
                                                     @PathVariable String taskSequence) {
        ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask, projectIdentifier, taskSequence);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping(path = "/{projectIdentifier}/{taskSequence}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String projectIdentifier,
                                               @PathVariable String taskSequence) {
        projectTaskService.deleteProjectTask(projectIdentifier, taskSequence);
        return ResponseEntity.ok("ProjectTask " + projectIdentifier + "/" + taskSequence + " has been deleted");
    }
}

