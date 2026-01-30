package lv.venta.repo;



import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.StudentProgram;
import lv.venta.model.StudentProgramCourse;
import lv.venta.model.StudyCourses;


public interface IStudentProgramCourseRepo extends CrudRepository<StudentProgramCourse, Long>{

	public abstract boolean existsByStudentProgramAndCourse(StudentProgram student, StudyCourses course);

	public abstract List<StudentProgramCourse> findByCourse(StudyCourses foundedCourse);

	

}
