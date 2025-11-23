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
@Table(name = "tests")
@Entity
public class CourseTests {

	@Setter(value = AccessLevel.NONE)
	@Column(name = "test_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long testID;

	@NotNull
	@Column(name = "test_title")
	private String testTitle;

	@NotNull
	@Column(name = "description")
	//@Pattern(regexp="[A-Za-zĀāČčĒēĢģĪīĶķĻļŅņŠšŪūŽž0-9.,_:;()\\\\s]{5,1000}") //allow any symbols from 5 to 1000
	@Size(min = 5, max = 10000)
	private String testDescription;

	@NotNull
	@Column(name = "total_points")
	private int points;

	// ----------Table connection-----------------//
	@ManyToOne
	@JoinColumn(name = "course_id")
	private StudyCourses course;

	@OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
	private Collection<TestTask> tasksForTest;
	// -------------------------------------------

	public CourseTests(String title, String description, int points) {
		setTestTitle(title);
		setTestDescription(description);
		setPoints(points);
	}
}
