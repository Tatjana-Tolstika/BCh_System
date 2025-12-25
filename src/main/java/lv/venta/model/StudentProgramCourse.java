package lv.venta.model;



import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name="student_program_course")
@Entity
public class StudentProgramCourse {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "student_program_course_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long studentProgramCourseID;
	
	@NotNull
	@Min(1)
	@Max(10)
	@Column(name="mark")
	private int mark;
	
	//----------Table connections-----------------------
	@ManyToOne
	@JoinColumn(name="student_program_id", nullable = false)
	private StudentProgram studentProgram;

	@ManyToOne
	@JoinColumn(name="course_id")
	private StudyCourses course;
	
	@OneToMany(mappedBy="studentProgramCourse" , cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<TestResult> testResult ;
	//--------------------------------------------------
	
	public StudentProgramCourse(StudentProgram student, StudyCourses course, int mark) {
		setStudentProgram(student);
		setCourse(course);
		setMark(mark);
	}
	
}
