package project.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.domain.Project;
import project.services.MapValidationErrorService;
import project.services.ProjectService;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final MapValidationErrorService mapValidationErrorService;

    @PostMapping(path = "")
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project, BindingResult bindingResult) {

        Optional<Map<String, String>> errorMap = mapValidationErrorService.formatBindingResult(bindingResult);
        if (errorMap.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap.get());
        }

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
        return ResponseEntity.ok("Project with ID: " + projectId + " has been successfully deleted");
    }
}
