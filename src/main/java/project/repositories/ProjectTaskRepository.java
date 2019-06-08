package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.domain.ProjectTask;

import java.util.Collection;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
    Collection<ProjectTask> findByProjectIdentifierOrderByPriority(String projectIdentifier);
    ProjectTask findByProjectSequence(String sequence);
}
