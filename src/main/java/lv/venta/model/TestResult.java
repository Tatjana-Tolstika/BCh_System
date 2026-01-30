package lv.venta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="test_results")
@Entity
public class TestResult {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "result_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long resultId;
	
	@NotNull
	@Column(name = "comments")
	private String comments;
	
	@NotNull
	@Column(name="minus")
	private double minus;
	
	//-----------Table Connections-------------------------------------
	@ManyToOne
	@JoinColumn(name="task_id")
	private TestTask task;
	
	
	
	@ManyToOne
	@JoinColumn(name="student_program_course_id")
	private StudentProgramCourse studentProgramCourse;
	
	//------------------------------------------------------------------
	
	public TestResult(String comments, double minus, TestTask task, StudentProgramCourse student) {
		setComments(comments);
		setMinus(minus);
		setTask(task);
		setStudentProgramCourse(student);
	}
	
}
