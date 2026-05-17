package lv.venta.model;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
@Table(name="lecturers")
@Entity
public class Lecturers {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "lecturer_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long lecturersId;

	
	@NotNull
	@Pattern(regexp="^[A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž]+([\\s-][A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž]++)*$")
	@Column(name = "lecturer_name")
	private String lecturerName;
	
	@NotNull
	@Pattern(regexp="^[A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž]+")
	@Column(name = "lecturer_surname")
	private String lecturerSurname;
	
	@NotNull
	@Column(name = "lecturer_degree")
	private String lecturerDegree;
	
	@ManyToMany
	@JoinTable(name = "lecturers_courses", joinColumns = @JoinColumn(name="lecturer_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private Set<StudyCourses> courses = new HashSet<>();
	
	@OneToOne(mappedBy = "lecturer")
    private MyUser user;
	
	public Lecturers(String name, String surname, String degree) {
		setLecturerName(name);
		setLecturerSurname(surname);
		setLecturerDegree(degree);
		
		
	}
}
