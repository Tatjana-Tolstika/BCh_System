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
@Table(name="lecturers")
@Entity
public class Lecturers {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "lecturers_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long lecturersID;
	
	@NotNull
	@Pattern(regexp="[A-Za-zĀāČčĒēĢģĪīĶķĻļŅņŠšŪūŽž]+")
	@Column(name = "lecturer_name")
	private String lecturerName;
	
	@NotNull
	@Pattern(regexp="[A-Za-zĀāČčĒēĢģĪīĶķĻļŅņŠšŪūŽž]+")
	@Column(name = "lecturer_surname")
	private String lecturerSurname;
	
	@NotNull
	@Column(name = "lecturer_degree")
	private String lecturerDegree;
	
	public Lecturers(String name, String surname, String degree) {
		setLecturerName(name);
		setLecturerSurname(surname);
		setLecturerDegree(degree);
		
		
	}
}
