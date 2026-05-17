package lv.venta.model;


import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	@Pattern(regexp="^[A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž]+([\\s-][A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž]++)*$",
			  message = "Name must start with a capital letter and contain only letters")
	@Column(name = "name")
	private String studentName;
	
	@NotNull
	@Pattern(regexp="^[A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž]+",
			  message = "Surname must start with a capital letter and contain only letters")
	@Column(name = "surname")
	private String studentSurname;
	
	@NotNull
	@Column(name = "matriculation_nr")
	@Pattern(regexp="[0-9]{4,10}")
	private String matriculationNr;
	
	@NotNull
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}$", 
			message = "Invalid email address")
	@Column(name = "email")
	private String email;
	
	@OneToOne(mappedBy = "student")
    private MyUser user;
	
	//----------2. Table connections---------------------------------------
	@OneToMany(mappedBy="student", cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	private Collection<StudentProgram> studentProgram;
	
	//------------------------------------------------------------------
	
	//3. Constructor
	public Students(String studentName ,String studentSurname , String matriculationNr, String email) {
		setStudentName(studentName);
		setStudentSurname(studentSurname);
		setMatriculationNr(matriculationNr);
		setEmail(email);
		
	}

	
	
}
