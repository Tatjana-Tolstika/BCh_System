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
@Table(name="Lecturers")
@Entity
public class Test {
	
	@Setter(value = AccessLevel.NONE)
	@Column(name = "TestID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long testID;
	
	@NotNull
	@Column(name = "TestTitle")
	private String testTitle;
	
	@NotNull
	@Column(name = "Description")
	private String testDescription;
	
	@NotNull
	@Column(name = "TotalPoints")
	private int points;
	
	//----------Table connection-----------------
	@ManyToOne
	@JoinColumn(name= "CourseID")
	private StudyCourses course;
}
