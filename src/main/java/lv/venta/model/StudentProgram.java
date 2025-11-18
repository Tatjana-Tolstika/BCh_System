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
@Table(name="StudentInProgram")
@Entity
public class StudentProgram {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "StudentProgramID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long studentProgramID;
	
	@NotNull
	@Column(name = "Course")
	private int course;
	
	//-----------Table connections--------------
	@ManyToOne
	@JoinColumn(name="StudentID")
	private Students student;
	
	@ManyToOne
	@JoinColumn(name="ProgramID")
	private StudyProgram studyProgram;
	
	//---------------------------------------------
	public StudentProgram(Students student, StudyProgram program,int course) {
		setStudent(student);
		setStudyProgram(program);
		setCourse(course);
	}
}
