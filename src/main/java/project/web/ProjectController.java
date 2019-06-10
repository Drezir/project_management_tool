package project.web;

import java.security.Principal;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.domain.Project;
import project.services.ProjectService;

@RestController
@RequestMapping(path = "/api/project")
@RequiredArgsConstructor
@CrossOrigin
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(path = "")
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project, Principal principal) {
        Project createdProject = projectService.saveOrUpdate(project, principal.getName());
        return ResponseEntity.ok(createdProject);
    }

    @GetMapping(path = "/{projectId}")
    public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectId, Principal principal) {
        Project project = projectService.findByProjectIdentifier(projectId, principal.getName());
        return ResponseEntity.ok(project);
    }

    @GetMapping(path = "/all")
    public Iterable<Project> listAllProjects(Principal principal) {
        return projectService.listAllProjects(principal.getName());
    }

    @DeleteMapping(path = "/{projectId}")
    public ResponseEntity<?> deleteProjectByIdentifier(@PathVariable String projectId, Principal principal) {
        projectService.deleteProjectByIdentifier(projectId, principal.getName());
        return ResponseEntity.ok("Project addValidationError ID: " + projectId + " has been successfully deleted");
    }
}
