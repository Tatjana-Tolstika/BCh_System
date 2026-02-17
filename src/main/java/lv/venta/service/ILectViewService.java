package lv.venta.service;

import java.util.List;

import lv.venta.model.CourseTests;
import lv.venta.model.Lecturers;
import lv.venta.model.Students;
import lv.venta.model.StudyCourses;

public interface ILectViewService {

	public abstract List<StudyCourses> allCoursesForLecturer(long lectId) throws Exception;

	public abstract List<CourseTests> allTestsByCourse(long courseId) throws Exception;

	public abstract List<Students> allStudentsOfTest(long testId) throws Exception;

	public abstract Lecturers getAuthorisedId() throws Exception;

}
