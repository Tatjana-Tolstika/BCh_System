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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
@Table(name="StudentProgramCourse")
@Entity
public class StudentProgramCourse {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "StudentProgramCourseID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long studentProgramCourseID;
	
	@NotNull
	@Min(1)
	@Max(10)
	@Column(name="Mark")
	private int mark;
	
	//----------Table connections-----------------------
	@ManyToOne
	@JoinTable(name="StudentID")
	private Students student;
	
	@ManyToOne
	@JoinTable(name="CourseID")
	private StudyCourses course;
	
	@OneToMany(mappedBy="student")
	private Collection<TestResult> testResult;
	//--------------------------------------------------
	
	public StudentProgramCourse(Students student, StudyCourses course, int mark) {
		setStudent(student);
		setCourse(course);
		setMark(mark);
	}
	
}
