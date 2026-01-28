package lv.venta.repo;



import org.springframework.data.repository.CrudRepository;

import lv.venta.model.StudentProgram;
import lv.venta.model.StudentProgramCourse;
import lv.venta.model.StudyCourses;


public interface IStudentProgramCourseRepo extends CrudRepository<StudentProgramCourse, Long>{

	public abstract boolean existsByStudentProgramAndCourse(StudentProgram student, StudyCourses course);

	

}
