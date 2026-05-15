package lv.venta.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lv.venta.model.CourseTests;
import lv.venta.model.Lecturers;
import lv.venta.model.Students;
import lv.venta.model.StudyCourses;

public interface ILectViewService {

	public abstract List<StudyCourses> allCoursesForLecturer(long lectId) throws Exception;

	public abstract List<CourseTests> allTestsByCourse(long courseId) throws Exception;

	public abstract List<Students> allStudentsOfTestResults(long testId) throws Exception;

	public abstract Lecturers getAuthorisedId();


	public abstract double testPointsCounter(long testId) throws Exception;

	public abstract double getStudentResult(long testId, long studentId) throws Exception;

	public abstract String controlTestVisibility(long testId, String testDeadline) throws Exception;

	public abstract List<String> getStudentFiles(long testId, long studentId) throws Exception;

	public abstract double getAverageMark(long testId) throws Exception;

	public abstract void uploadZipTests(long testId, MultipartFile file) throws Exception;

	public abstract String runTestsForStudent(long testId, long studentId) throws Exception;

	public abstract List<Double> getAllMarksForTest(long testId) throws Exception;

	





}
