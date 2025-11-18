package lv.venta.model;

import java.util.Collection;

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
@Table(name="Study program")
@Entity
public class StudyProgram {
	//1. Variables
		@Setter(value = AccessLevel.NONE)
		@Column(name = "ProgramID")
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private long programID;
		
		@NotNull
		@Pattern(regexp = "[A-Za-z ]+")
		@Column(name = "Title")
		private String programTitle;
		
		@NotNull
		@Column(name = "Degree")
		private String degree;
		
		@NotNull
		@Pattern(regexp = "[A-sZ]+")
		@Column(name = "Abbreviation")
		private String abbreviation;
		
		//------------------Table connections------------------------
		@OneToMany(mappedBy="studyProgram")
		private Collection<StudentProgram> studentProgram;
		
		//-----------------------------------------------------------
		
		public StudyProgram(String title, String degree, String abbreviation) {
			setProgramTitle(title);
			setDegree(degree);
			setAbbreviation(abbreviation);
		}
}
