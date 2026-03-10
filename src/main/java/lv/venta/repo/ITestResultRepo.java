package lv.venta.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.StudentProgramCourse;
import lv.venta.model.TestResult;
import lv.venta.model.TestTask;

public interface ITestResultRepo extends CrudRepository<TestResult, Long>{


	public abstract boolean existsByTaskAndStudentProgramCourse(TestTask task, StudentProgramCourse student);

	public abstract List<TestResult> findByTask_Test_TestIdAndStudentProgramCourse_StudentProgram_Student_StudentId(
			long testId, long studentId);


}
