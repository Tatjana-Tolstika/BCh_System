package lv.venta.model;


import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name="students")
@Entity

public class Students {

	//1. Variables
	@Setter(value = AccessLevel.NONE)
	@Column(name = "student_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long studentId;
	
	@NotNull
	@Pattern(regexp="[A-Za-zĀāČčĒēĢģĪīĶķĻļŅņŠšŪūŽž]+")
	@Column(name = "name")
	private String studentName;
	
	@NotNull
	@Pattern(regexp="[A-Za-zĀāČčĒēĢģĪīĶķĻļŅņŠšŪūŽž]+")
	@Column(name = "surname")
	private String studentSurname;
	
	@NotNull
	@Column(name = "matriculation_nr")
	private long matriculationNr;
	
	@NotNull
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
	@Column(name = "email")
	private String email;
	
	//----------2. Table connections---------------------------------------
	@OneToMany(mappedBy="student", cascade = CascadeType.ALL)
	@ToString.Exclude
	private Collection<StudentProgram> studentProgram;
	
	@OneToMany(mappedBy="student", cascade = CascadeType.ALL)
	@ToString.Exclude
	private Collection<StudentProgramCourse> studentProgramCourse;
	//------------------------------------------------------------------
	
	//3. Constructor
	public Students(String studentName ,String studentSurname , long matriculationNr, String email) {
		setStudentName(studentName);
		setStudentSurname(studentSurname);
		setMatriculationNr(matriculationNr);
		setEmail(email);
		
	}

	
	
}
