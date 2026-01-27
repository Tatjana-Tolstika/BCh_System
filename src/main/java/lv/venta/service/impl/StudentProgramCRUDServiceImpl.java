package lv.venta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.CourseTests;
import lv.venta.model.StudentProgram;
import lv.venta.model.Students;
import lv.venta.model.StudyProgram;
import lv.venta.model.TestTask;
import lv.venta.repo.IStudentProgramRepo;
import lv.venta.repo.IStudentsRepo;
import lv.venta.repo.IStudyProgramRepo;
import lv.venta.service.IStudentProgramCRUDService;

@Service
public class StudentProgramCRUDServiceImpl implements IStudentProgramCRUDService{
	
	@Autowired
	private IStudentProgramRepo studentPrRepo;
	@Autowired
	private IStudyProgramRepo programRepo;
	@Autowired
	private IStudentsRepo studentRepo;
	
	//=======================CRUD====================================================================================
	
			//----------------CREATE-------------------------------------------------------------
			@Override
			public void createStudentProgram(Students student, StudyProgram program, int course) throws Exception {
				if(student == null || course <= 0 || program == null) {
					throw new Exception("Incorrect input parameters!");
				}
				
				if(studentPrRepo.existsByStudentAndStudyProgramAndCourse(student, program, course)) {
					throw new Exception("Student in the program you want to add is already in the list!");
				}
				else {
					StudentProgram newStudentProgram = new StudentProgram(student, program, course);
					studentPrRepo.save(newStudentProgram);
				}
			}
			
			//------------------------------------------------------------------------------------
			//--------------RETRIEVE--------------------------------------------------------------
			@Override 
			public StudentProgram retrieveStudentProgramById(long id) throws Exception{
				if(id < 0) {
					throw new Exception("Choose correct ID!");
				}
				if(!studentPrRepo.existsById(id)) {
					throw new Exception("StudentProgram with ID [ " + id + " ] doesn't exists!");
				}
				StudentProgram retrievedStudentProgram = studentPrRepo.findById(id).get();
				
				return retrievedStudentProgram;
			}
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++
			@Override
			public List<StudentProgram> retrieveByStudyProgram(long programId) throws Exception {
			    StudyProgram program = programRepo.findById(programId)
			        .orElseThrow(() -> new Exception("Program not found"));

			    List<StudentProgram> result = studentPrRepo.findByStudyProgram(program);

			    if (result.isEmpty()) {
			        throw new Exception("No students found for this program");
			    }

			    return result;
			}
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++
			@Override
			public List<StudentProgram> retrieveByStudent(long studentId) throws Exception {
			    Students student = studentRepo.findById(studentId)
			        .orElseThrow(() -> new Exception("Student not found"));

			    List<StudentProgram> result = studentPrRepo.findByStudent(student);

			    if (result.isEmpty()) {
			        throw new Exception("Student is not assigned to any program");
			    }

			    return result;
			}
			
			//------------------------------------------------------------------------------------
			
}
