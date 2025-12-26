package lv.venta.model;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name="study_courses")
@Entity
public class StudyCourses {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "course_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long courseID;
	
	@NotNull
	@Column(name = "course_title")
	private String courseTitle;
	
	@NotNull
	@Column(name = "credits")
	private int credits;
	
	//----------Table connections-----------------------
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private Collection<CourseTests> courseTests;
	
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private Collection<StudentProgramCourse> studentProgramCourse;
	
	@ManyToMany(mappedBy = "courses")
	private Set<Lecturers> lecturers = new HashSet<>();
	
	//--------------------------------------------------
	
	public StudyCourses(String title, int credits) {
		setCourseTitle(title);
		setCredits(credits);
	}
}
