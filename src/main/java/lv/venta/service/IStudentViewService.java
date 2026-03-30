package lv.venta.service;

import java.nio.file.Path;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lv.venta.model.CourseTests;
import lv.venta.model.Students;
import lv.venta.model.StudyCourses;
import lv.venta.model.TestResult;

public interface IStudentViewService {

	public abstract List<StudyCourses> coursesForStudent(long studentId) throws Exception;

	public abstract Students getAuthorisedId();

	public abstract List<TestResult> resultsForStudent(long studentId, long testId) throws Exception;

	public abstract List<CourseTests> allTestsByCourseAndStatus(long courseId) throws Exception;

	public abstract void uploadZip(long testId, MultipartFile file, String username) throws Exception;


}
