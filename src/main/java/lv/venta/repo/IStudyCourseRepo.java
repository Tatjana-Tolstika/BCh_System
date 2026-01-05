package lv.venta.repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.StudyCourses;

public interface IStudyCourseRepo extends CrudRepository<StudyCourses, Long>{

	public abstract boolean existsByCourseTitleAndCredits(String title, int credits);

}
