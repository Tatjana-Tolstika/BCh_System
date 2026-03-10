package lv.venta.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.Lecturers;
import lv.venta.model.StudyCourses;

public interface IStudyCourseRepo extends CrudRepository<StudyCourses, Long>{

	public abstract boolean existsByCourseTitleAndCredits(String title, int credits);
	public abstract StudyCourses findByCourseTitle(String courseTitle);
	public abstract List<StudyCourses> findByLecturers(Lecturers lecturer);
	public abstract List<StudyCourses> findByStudentProgramCourseStudentProgramStudentStudentId(long studentId);

}
