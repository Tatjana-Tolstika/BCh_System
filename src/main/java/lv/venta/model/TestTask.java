package lv.venta.model;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name="Study courses")
@Entity
public class TestTask {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "TaskID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long taskID;
	
	@NotNull
	@Pattern(regexp="[A-za-z.,_:; ]{5,150}")
	@Column(name = "TaskDescription")
	private String taskDescription;
	
	@NotNull
	@Column(name="MaxPointsAmount")
	private int maxPoints;
	
	//---------Tables connections---------------------------
	@ManyToOne
	@JoinColumn(name="TestID")
	private CourseTests test;
	
	
	//-----------------------------------------------------
	
	public TestTask(String description, int points) {
		setTaskDescription(description);
		setMaxPoints(points);
	}
}
