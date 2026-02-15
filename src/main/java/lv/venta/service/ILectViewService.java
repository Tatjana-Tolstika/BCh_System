package lv.venta.service;

import java.util.List;

import lv.venta.model.StudyCourses;

public interface ILectViewService {

	public abstract List<StudyCourses> allCoursesForLecturer(long lectId) throws Exception;

}
