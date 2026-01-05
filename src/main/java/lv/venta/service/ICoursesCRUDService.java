package lv.venta.service;

import java.util.ArrayList;

import lv.venta.model.StudyCourses;

public interface ICoursesCRUDService {

	public abstract void createCourse(String title, int credits) throws Exception;

	public abstract StudyCourses retrieveCourseById(long id) throws Exception;

	public abstract void updateCourseById(long id, String title, int credits) throws Exception;

	public abstract void deleteCourse(long id) throws Exception;

	public abstract ArrayList<StudyCourses> selectAllCourses() throws Exception;

}
