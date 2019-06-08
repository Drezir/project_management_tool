package project.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "projects")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Project name is required")
	private String projectName;

	@NotBlank(message = "Project identifier is required")
	@Length(min = 4, max = 5, message = "Project identifier has to be 4 or 5 characters long")
	@Column(updatable = false, unique = true)
	private String projectIdentifier;

	@NotBlank(message = "Project description is required")
	private String description;

	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date startDate;

	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date endDate;

	@JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
	private LocalDateTime updatedAt;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "backlog_id")
	@JsonManagedReference
	private Backlog backlog;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
