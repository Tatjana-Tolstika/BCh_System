package lv.venta.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.CourseTests;
import lv.venta.model.StudyCourses;

public interface ICourseTestRepo extends CrudRepository<CourseTests, Long>{

	public abstract boolean existsByTestTitleAndTestDescriptionAndPointsAndCourse(String title, String description, int points,
			StudyCourses course);

	public abstract List<CourseTests> findByCourse(StudyCourses course);

}
