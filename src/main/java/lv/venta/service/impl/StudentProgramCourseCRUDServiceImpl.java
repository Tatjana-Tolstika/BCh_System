package lv.venta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.CourseTests;
import lv.venta.model.StudentProgram;
import lv.venta.model.StudentProgramCourse;
import lv.venta.model.StudyCourses;
import lv.venta.model.TestResult;
import lv.venta.model.TestTask;
import lv.venta.repo.IStudentProgramCourseRepo;
import lv.venta.repo.IStudentProgramRepo;
import lv.venta.repo.IStudyCourseRepo;
import lv.venta.service.IStudentProgramCourseCRUDService;

@Service
public class StudentProgramCourseCRUDServiceImpl implements IStudentProgramCourseCRUDService{
	@Autowired
	private IStudentProgramCourseRepo spcRepo;
	@Autowired
	private IStudentProgramRepo studentRepo;
	@Autowired
	private IStudyCourseRepo courseRepo;
	
	//================CRUD===================================================================
	//----------------CREATE----------------------------------
	@Override
	public void createSPC(StudentProgram studentInput, StudyCourses courseInput, int mark) throws Exception {
		if( mark <= 0 || mark > 10) {
			throw new Exception("Incorrect input parameters!");
		}
		StudentProgram studentProgram = studentRepo.findById(studentInput.getStudentProgramId())
	            .orElseThrow(() -> new Exception("StudentProgram not found"));

	    StudyCourses course = courseRepo.findById(courseInput.getCourseId())
	            .orElseThrow(() -> new Exception("Course not found"));
		
		if(spcRepo.existsByStudentProgramAndCourse(studentInput, courseInput)) {
			throw new Exception("Result you want to create already exists!");
		}
		else {
			StudentProgramCourse newSPC = new StudentProgramCourse(studentProgram, course, mark);
			spcRepo.save(newSPC);
		}
	}
	//--------------------------------------------------------
	//--------------RETRIEVE--------------------------------------------------------------
	@Override 
	public StudentProgramCourse retrieveSPCById(long id) throws Exception{
		if(id < 0) {
			throw new Exception("Choose correct ID!");
		}
		if(!spcRepo.existsById(id)) {
			throw new Exception("student program course with ID [ " + id + " ] doesn't exists!");
		}
		StudentProgramCourse retrievedResult = spcRepo.findById(id).get();
		
		return retrievedResult;
	}
	
	//------------------------------------------------------------------------------------
	//---------------UPDATE---------------------------------------------------------------
	@Override
	public void updateStudentProgramCourseById(long spcId,long studentProgramId,long courseId,int mark) throws Exception {

	    StudentProgramCourse spc = spcRepo.findById(spcId).orElseThrow(() -> new Exception("StudentProgramCourse not found"));

	    if (mark < 1 || mark > 10) {
	        throw new Exception("Mark must be between 1 and 10");
	    }

	    StudentProgram studentProgram = studentRepo.findById(studentProgramId)
	            .orElseThrow(() -> new Exception("StudentProgram not found"));

	    StudyCourses course = courseRepo.findById(courseId)
	            .orElseThrow(() -> new Exception("Course not found"));

	    if (!(spc.getStudentProgram().getStudentProgramId() == studentProgramId
	          && spc.getCourse().getCourseId() == courseId)
	        && spcRepo.existsByStudentProgramAndCourse(studentProgram, course)
	    ) {
	        throw new Exception("This student already has a result for this course");
	    }

	    if (spc.getStudentProgram() == null ||
	        spc.getStudentProgram().getStudentProgramId() != studentProgramId) {
	        spc.setStudentProgram(studentProgram);
	    }

	    if (spc.getCourse() == null ||
	        spc.getCourse().getCourseId() != courseId) {
	        spc.setCourse(course);
	    }

	    if (spc.getMark() != mark) {
	        spc.setMark(mark);
	    }

	    spcRepo.save(spc);
	}

	//------------------------------------------------------------------------------------
	//--------------DELETE----------------------------------------------------------------

	@Override
	public void deleteSPCById(long id) throws Exception {
	    StudentProgramCourse spc = retrieveSPCById(id);
	    spcRepo.delete(spc);
	}

	//--------------------------------------------------------------------------------------
	
	//================================END CRUD=============================================
	@Override
	public List<StudentProgramCourse> selectAllSPC(){
		return (List<StudentProgramCourse>) spcRepo.findAll();
	}

}
