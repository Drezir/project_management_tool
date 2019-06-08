package project.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.domain.Project;
import project.services.ProjectService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/project")
@RequiredArgsConstructor
@CrossOrigin
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(path = "")
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project) {
        Project createdProject = projectService.saveOrUpdate(project);
        return ResponseEntity.ok(createdProject);
    }

    @GetMapping(path = "/{projectId}")
    public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectId) {
        Project project = projectService.findByProjectIdentifier(projectId);
        return ResponseEntity.ok(project);
    }

    @GetMapping(path = "/all")
    public Iterable<Project> listAllProjects() {
        return projectService.listAllProjects();
    }

    @DeleteMapping(path = "/{projectId}")
    public ResponseEntity<?> deleteProjectByIdentifier(@PathVariable String projectId) {
        projectService.deleteProjectByIdentifier(projectId);
        return ResponseEntity.ok("Project addValidationError ID: " + projectId + " has been successfully deleted");
    }
}
