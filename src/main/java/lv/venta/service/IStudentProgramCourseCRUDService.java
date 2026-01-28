package lv.venta.service;

import java.util.List;

import lv.venta.model.StudentProgram;
import lv.venta.model.StudentProgramCourse;
import lv.venta.model.StudyCourses;

public interface IStudentProgramCourseCRUDService {

	public abstract void createSPC(StudentProgram student, StudyCourses course, int mark) throws Exception;

	public abstract StudentProgramCourse retrieveSPCById(long id) throws Exception;

	public abstract void updateStudentProgramCourseById(long spcId, long studentProgramId, long courseId, int mark) throws Exception;

	public abstract void deleteSPCById(long id) throws Exception;

	public abstract List<StudentProgramCourse> selectAllSPC();

}
