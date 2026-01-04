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
@Table(name="study_program")
@Entity
public class StudyProgram {
	//1. Variables
		@Setter(value = AccessLevel.NONE)
		@Column(name = "program_id")
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private long programId;
		
		@NotNull
		@Pattern(regexp = "[A-Za-zĀāČčĒēĢģĪīĶķĻļŅņŠšŪūŽž ]+")
		@Column(name = "program_title")
		private String programTitle;
		
		@NotNull
		@Column(name = "program_degree")
		private String programDegree;
		
		@NotNull
		@Pattern(regexp = "[A-Z]+")
		@Column(name = "abbreviation")
		private String abbreviation;
		
		//------------------Table connections------------------------
		@OneToMany(mappedBy="studyProgram", cascade = CascadeType.ALL, orphanRemoval = true)
		private Collection<StudentProgram> studentProgram;
		
//		//-----------------------------------------------------------
		
		public StudyProgram(String title, String degree, String abbreviation) {
			setProgramTitle(title);
			setProgramDegree(degree);
			setAbbreviation(abbreviation);
		}
}
