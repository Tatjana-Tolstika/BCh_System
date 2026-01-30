package lv.venta.service;

import java.util.ArrayList;
import java.util.List;

import lv.venta.model.CourseTests;
import lv.venta.model.StudyCourses;

public interface ICourseTestCRUDService {

	public abstract void createCourseTest(String title, String description, int points, StudyCourses course) throws Exception;

	public abstract CourseTests retrieveTestById(long id) throws Exception;


	public abstract void deleteTest(long id) throws Exception;

	public abstract ArrayList<CourseTests> selectAllTests() throws Exception;

	public abstract List<StudyCourses> selectAllCourse();

	

	public abstract void updateTestById(long id, String title, String description, int points, long  course) throws Exception;

	public abstract StudyCourses selectCourseByTest(long testId) throws Exception;
	


}
