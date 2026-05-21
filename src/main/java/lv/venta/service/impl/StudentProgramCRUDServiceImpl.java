package lv.venta.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lv.venta.model.StudentProgram;
import lv.venta.model.Students;
import lv.venta.model.StudyProgram;
import lv.venta.repo.IStudentProgramRepo;
import lv.venta.repo.IStudentsRepo;
import lv.venta.repo.IStudyProgramRepo;
import lv.venta.service.IStudentProgramCRUDService;

@Service
public class StudentProgramCRUDServiceImpl implements IStudentProgramCRUDService{
	
	
	private IStudentProgramRepo studentPrRepo;
	private IStudyProgramRepo programRepo;
	private IStudentsRepo studentRepo;
	public StudentProgramCRUDServiceImpl (IStudentProgramRepo studentPrRepo, IStudyProgramRepo programRepo, IStudentsRepo studentRepo) 
		{this.studentPrRepo = studentPrRepo;
		this.programRepo = programRepo;
		this.studentRepo = studentRepo;}
	
	
	//=======================CRUD====================================================================================
	
			//----------------CREATE-------------------------------------------------------------
			@Override
			public void createStudentProgram(Students studentInput, StudyProgram programInput, int course) throws Exception {
				
				if(studentInput == null || course <= 0 || programInput == null|| course > programInput.getLength()) {
					throw new Exception("Incorrect input parameters!");
				}
				Students student = studentRepo.findById(studentInput.getStudentId())
			            .orElseThrow(() -> new Exception("Student not found"));

			    StudyProgram program = programRepo.findById(programInput.getProgramId())
			            .orElseThrow(() -> new Exception("Program not found"));
				if(studentPrRepo.existsByStudent(studentInput)) {
					throw new Exception("Student is already in one of the programs!");
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
				
				return studentPrRepo.findById(id)
						.orElseThrow(() -> new Exception("Student in program with ID [ " + id + " ] doesn't exist!"));
			}
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++
			@Override
			public List<StudentProgram> retrieveByStudyProgram(long programId) throws Exception {
			    StudyProgram program = programRepo.findById(programId)
			        .orElseThrow(() -> new Exception("Program not found"));

			    List<StudentProgram> result = studentPrRepo.findByStudyProgram(program);

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
			//--------------DELETE----------------------------------------------------------------

			@Override
			public void deleteStudentProgramById(long id) throws Exception {
			     if(id < 0) {
			    	 throw new Exception("ID is wrong!");
			     }
			     StudentProgram stProgram = retrieveStudentProgramById(id);
			     studentPrRepo.delete(stProgram);
			}
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++
			@Override 
			public void deleteStudentFromProgram(long studentId, long programId) throws Exception {

			    Students student = studentRepo.findById(studentId)
			        .orElseThrow(() -> new Exception("Student not found"));

			    StudyProgram program = programRepo.findById(programId)
			        .orElseThrow(() -> new Exception("Program not found"));

			    StudentProgram sp = studentPrRepo.findByStudentAndStudyProgram(student, program);
	    		if(sp == null) {
			    	throw new Exception("The student you want to delete is not in the program!");
			    }
			        

			    studentPrRepo.delete(sp);
			}

			//--------------------------------------------------------------------------------------
			//---------------UPDATE---------------------------------------------------------------
			@Override 
			public void updateStudentProgramById( long id,long studentId,long programId,int course) throws Exception {

			    StudentProgram sp = retrieveStudentProgramById(id);

			    

			    Students student = studentRepo.findById(studentId)
			        .orElseThrow(() -> new Exception("Student not found"));

			    StudyProgram program = programRepo.findById(programId)
			        .orElseThrow(() -> new Exception("Program not found"));
			    
			    if (course <= 0 || course > program.getLength()) {
			        throw new Exception("Incorrect course value!");
			    }
			    // Checking if the student in this program already exists
			    if (!(sp.getStudent().getStudentId() == studentId &&
			    	      sp.getStudyProgram().getProgramId() == programId &&
			    	      sp.getCourse() == course) &&studentPrRepo.existsByStudentAndStudyProgramAndCourse(student, program, course)) {
			        throw new Exception("Student is already enrolled in this program");
			    }

			    // Student
			    if (sp.getStudent() == null ||
			        sp.getStudent().getStudentId() != studentId) {
			        sp.setStudent(student);
			    }

			    // Program
			    if (sp.getStudyProgram() == null ||
			        sp.getStudyProgram().getProgramId() != programId) {
			        sp.setStudyProgram(program);
			    }

			    // Course
			    if (sp.getCourse() != course) {
			        sp.setCourse(course);
			    }

			    studentPrRepo.save(sp);
			}
			//------------------------------------------------------------------------------------
			//===========================END OF CRUD==================================================================
			//--------------------------------------------------------------------------------------
			@Override
			public List<StudentProgram> selectAllStudentProgram(){
				return (List<StudentProgram>) studentPrRepo.findAll();
			}
			
			@Override
			public StudyProgram getProgramById(long id) throws Exception{
				return programRepo.findById(id)
				        .orElseThrow(() -> new Exception("Program not found"));
			}
			
			
			
}
