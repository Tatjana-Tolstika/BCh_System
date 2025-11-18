package lv.venta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="Study courses")
@Entity
public class StudyCourses {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "CourseID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long courseID;
	
	@NotNull
	@Column(name = "Title")
	private String courseTitle;
	
	@NotNull
	@Column(name = "Credits")
	private int credits;
	
	public StudyCourses(String title, int credits) {
		setCourseTitle(title);
		setCredits(credits);
	}
}
