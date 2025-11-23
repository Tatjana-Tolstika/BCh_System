package lv.venta.model;


import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name="test_tasks")
@Entity
public class TestTask {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "task_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long taskID;
	
	@NotNull
//	@Pattern(regexp="[A-za-zĀāČčĒēĢģĪīĶķĻļŅņŠšŪūŽž.,_:; ]{5,500}")
	@Size(min = 5, max = 10000)
	@Column(name = "task_description")
	private String taskDescription;
	
	@NotNull
	@Column(name="max_points_amount")
	private double maxPoints;
	
	//---------Tables connections---------------------------
	@ManyToOne
	@JoinColumn(name="test_id")
	private CourseTests test;
	
	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
	private Collection<TestResult> testResults;
	
	
	//-----------------------------------------------------
	
	public TestTask(CourseTests test, String description, double points) {
		setTest(test);
		setTaskDescription(description);
		setMaxPoints(points);
	}
}
