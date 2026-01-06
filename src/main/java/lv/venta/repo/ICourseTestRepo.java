package lv.venta.repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.CourseTests;
import lv.venta.model.StudyCourses;

public interface ICourseTestRepo extends CrudRepository<CourseTests, Long>{

	public abstract boolean existsByTestTitleAndTestDescriptionAndPointsAndCourse(String title, String description, int points,
			StudyCourses course);

}
