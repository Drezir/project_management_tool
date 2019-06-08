package project.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Data
@Table(name = "backlogs")
public class Backlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer PTSequence = 0;
    private String projectIdentifier;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private Project project;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "backlog", orphanRemoval = true, cascade = CascadeType.REFRESH)
    @JsonManagedReference
    private Collection<ProjectTask> projectTasks = new HashSet<>();
}
