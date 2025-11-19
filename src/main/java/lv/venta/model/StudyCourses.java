package lv.venta.model;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name="Study courses")
@Entity
public class StudyCourses {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "CourseID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long courseID;
	
	@NotNull
	@Column(name = "Title")
	private String courseTitle;
	
	@NotNull
	@Column(name = "Credits")
	private int credits;
	
	//----------Table connections-----------------------
	@OneToMany(mappedBy = "course")
	private Collection<CourseTests> courseTests;
	
	
	@OneToMany(mappedBy = "course")
	private Collection<StudentProgramCourse> studentProgramCourse;
	
	//--------------------------------------------------
	
	public StudyCourses(String title, int credits) {
		setCourseTitle(title);
		setCredits(credits);
	}
}
