package lv.venta.service;

import java.util.List;

import lv.venta.model.StudentProgram;
import lv.venta.model.Students;
import lv.venta.model.StudyProgram;

public interface IStudentProgramCRUDService {

	public abstract void createStudentProgram(Students student, StudyProgram program, int course) throws Exception;

	public abstract StudentProgram retrieveStudentProgramById(long id) throws Exception;

	public abstract List<StudentProgram> retrieveByStudyProgram(long programId) throws Exception;

	public abstract List<StudentProgram> retrieveByStudent(long studentId) throws Exception;

	public abstract void deleteStudentProgramById(long id) throws Exception;

	public abstract void deleteStudentFromProgram(long studentId, long programId) throws Exception;

	public abstract void updateStudentProgramById(long id, long studentId, long programId, int course) throws Exception;

	public abstract List<StudentProgram> selectAllStudentProgram();

	public abstract StudyProgram getProgramById(long id) throws Exception;

}
