package lv.venta.service;

import java.util.List;

import lv.venta.model.StudentProgramCourse;
import lv.venta.model.TestResult;
import lv.venta.model.TestTask;

public interface ITestResultCRUDService {

	public abstract void createResult(String comments, double minus, TestTask task, StudentProgramCourse student) throws Exception;

	public abstract TestResult retrieveResultById(long id) throws Exception;


	public abstract void deleteResultById(long id) throws Exception;

	public abstract void updateTestResultById(long resultId, long studentProgramCourseId, long taskId, String comments, double minus)throws Exception;

	public abstract List<TestResult> selectAllResults();

	public abstract List<TestResult> selectResultByTestAndStudentId(long testId, long studentId) throws Exception;

}
