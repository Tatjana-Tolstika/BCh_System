package lv.venta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="Lecturers")
@Entity
public class Lecturers {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "LecturersID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long lecturersID;
	
	@NotNull
	@Pattern(regexp="[A-Za-z]+")
	@Column(name = "LecturerName")
	private String lecturerName;
	
	@NotNull
	@Pattern(regexp="[A-Za-z]+")
	@Column(name = "LecturerSurname")
	private String lecturerSurname;
	
	@NotNull
	@Column(name = "LecturerDegree")
	private String lecturerDegree;
	
	public Lecturers(String name, String surname, String degree) {
		setLecturerName(name);
		setLecturerSurname(surname);
		setLecturerDegree(degree);
		
		
	}
}
