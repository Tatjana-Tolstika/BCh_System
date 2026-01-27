package lv.venta.repo;



import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.StudentProgram;
import lv.venta.model.Students;
import lv.venta.model.StudyProgram;


public interface IStudentProgramRepo  extends CrudRepository<StudentProgram, Long>{

	public abstract boolean existsByStudentAndStudyProgramAndCourse(Students student, StudyProgram program, int course);

	public abstract List<StudentProgram> findByStudyProgram(StudyProgram program);

	public abstract List<StudentProgram> findByStudent(Students student);



	public abstract boolean existsByStudent(Students student);

	public abstract StudentProgram findByStudentAndStudyProgram(Students student, StudyProgram program);





}
