package lv.venta.service;

import java.util.List;

import lv.venta.model.CourseTests;
import lv.venta.model.Students;
import lv.venta.model.StudyCourses;
import lv.venta.model.TestResult;

public interface IStudentViewService {

	public abstract List<StudyCourses> coursesForStudent(long studentId) throws Exception;

	public abstract Students getAuthorisedId();

	public abstract List<TestResult> resultsForStudent(long studentId, long testId) throws Exception;

	public abstract List<CourseTests> allTestsByCourseAndStatus(long courseId) throws Exception;


}
