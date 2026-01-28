package lv.venta.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.StudentProgramCourse;
import lv.venta.model.TestResult;
import lv.venta.model.TestTask;
import lv.venta.repo.IStudentProgramCourseRepo;
import lv.venta.repo.ITestResultRepo;
import lv.venta.repo.ITestTaskRepo;
import lv.venta.service.ITestResultCRUDService;
@Service
public class TestResultCRUDServiceImpl implements ITestResultCRUDService{
	
	@Autowired 
	private ITestResultRepo resultRepo;
	
	@Autowired
	private ITestTaskRepo taskRepo;
	@Autowired
	private IStudentProgramCourseRepo spcRepo;
	
	//=======================CRUD====================================================================================
	
	//----------------------------CREATE----------------------------------------------------------------------------
	@Override
	public void createResult(String comments, double minus, TestTask taskInput, StudentProgramCourse spcInput) throws Exception {
		if(comments == null || minus <= 0 ) {
			throw new Exception("Incorrect input parameters!");
		}
		TestTask task = taskRepo.findById(taskInput.getTaskId())
	            .orElseThrow(() -> new Exception("Task not found"));

	    StudentProgramCourse spc = spcRepo.findById(spcInput.getStudentProgramCourseId())
	            .orElseThrow(() -> new Exception("Student program course not found"));
		
		if(resultRepo.existsByTaskAndStudentProgramCourse(task, spc)) {
			throw new Exception("Result you want to create already exists!");
		}
		else {
			TestResult newResult = new TestResult(comments, minus, task, spc);
			resultRepo.save(newResult);
		}
	}
	//--------------------------------------------------------------------------------------------------------------
	//--------------RETRIEVE--------------------------------------------------------------
	@Override 
	public TestResult retrieveResultById(long id) throws Exception{
		if(id < 0) {
			throw new Exception("Choose correct ID!");
		}
		if(!resultRepo.existsById(id)) {
			throw new Exception("Result with ID [ " + id + " ] doesn't exists!");
		}
		TestResult retrievedResult = resultRepo.findById(id).get();
		
		return retrievedResult;
	}
	
	
	//------------------------------------------------------------------------------------
	//---------------UPDATE---------------------------------------------------------------
	@Override
	public void updateTestResultById(long resultId,long studentProgramCourseId, long taskId, String comments, double minus) throws Exception {

	    TestResult result = resultRepo.findById(resultId)
	            .orElseThrow(() -> new Exception("TestResult not found"));

	    if (comments == null || comments.trim().isEmpty()) {
	        throw new Exception("Comments must not be empty");
	    }
	    if (minus < 0) {
	        throw new Exception("Minus must be >= 0");
	    }
	    StudentProgramCourse spc = spcRepo.findById(studentProgramCourseId)
	            .orElseThrow(() -> new Exception("StudentProgramCourse not found"));

	    TestTask task = taskRepo.findById(taskId)
	            .orElseThrow(() -> new Exception("Task not found"));

	    if (
	        !(result.getStudentProgramCourse().getStudentProgramCourseId() == studentProgramCourseId
	          && result.getTask().getTaskId() == taskId)
	        && resultRepo.existsByStudentProgramCourseAndTask(spc, task)
	    ) {
	        throw new Exception("This student already has a result for this task");
	    }

	    if (result.getStudentProgramCourse() == null ||
	        result.getStudentProgramCourse().getStudentProgramCourseId() != studentProgramCourseId) {
	        result.setStudentProgramCourse(spc);
	    }

	    if (result.getTask() == null ||
	        result.getTask().getTaskId() != taskId) {
	        result.setTask(task);
	    }

	    if (!result.getComments().equals(comments.trim())) {
	        result.setComments(comments.trim());
	    }

	    if (result.getMinus() != minus) {
	        result.setMinus(minus);
	    }

	    resultRepo.save(result);
	}

	//------------------------------------------------------------------------------------
	//--------------DELETE----------------------------------------------------------------

	@Override
	public void deleteResultById(long id) throws Exception {
	    TestResult result = retrieveResultById(id);
	    resultRepo.delete(result);
	}

	//--------------------------------------------------------------------------------------
	//===========================END OF CRUD==================================================================
	@Override
	public List<TestResult> selectAllResults(){
		return (List<TestResult>) resultRepo.findAll();
	}

}
