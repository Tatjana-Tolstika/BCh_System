package lv.venta.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.StudentProgramCourse;
import lv.venta.model.TestResult;
import lv.venta.model.TestStatus;
import lv.venta.model.TestTask;

public interface ITestResultRepo extends CrudRepository<TestResult, Long>{


	public abstract boolean existsByTaskAndStudentProgramCourse(TestTask task, StudentProgramCourse student);

	public abstract List<TestResult> findByTaskTestTestIdAndStudentProgramCourseStudentProgramStudentStudentIdAndTaskTestStatus(
			long testId, long studentId, TestStatus published);

	public abstract List<TestResult> findByTaskTestTestIdAndStudentProgramCourseStudentProgramStudentStudentId(
			long testId, long studentId);

	


}
