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
@Table(name="student_in_program")
@Entity
public class StudentProgram {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "student_program_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long studentProgramID;
	
	@NotNull
	@Column(name = "course")
	private int course;
	
	//-----------Table connections--------------
	@ManyToOne
	@JoinColumn(name="student_id")
	private Students student;
	
	@ManyToOne
	@JoinColumn(name="program_id")
	private StudyProgram studyProgram;
	
	@OneToMany(
		    mappedBy = "studentProgram",
		    cascade = CascadeType.ALL,
		    orphanRemoval = true
		)
	@ToString.Exclude
	private Collection<StudentProgramCourse> courses;

	//---------------------------------------------
	public StudentProgram(Students student, StudyProgram program,int course) {
		setStudent(student);
		setStudyProgram(program);
		setCourse(course);
	}
}
