package lv.venta.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.CourseTests;
import lv.venta.model.TestTask;
import lv.venta.repo.ICourseTestRepo;
import lv.venta.repo.ITestTaskRepo;
import lv.venta.service.ITestTaskCRUDService;
@Service
public class TestTaskCRUDServiceImpl implements ITestTaskCRUDService{
	@Autowired
	private ITestTaskRepo taskRepo;
	
	@Autowired
	private ICourseTestRepo testRepo;
	
	//=======================CRUD====================================================================================
	
		//----------------CREATE-------------------------------------------------------------
		@Override
		public void createTask(CourseTests test, String description, double points) throws Exception {
			if(description == null || points <= 0 ) {
				throw new Exception("Incorrect input parameters!");
			}
			
			if(taskRepo.existsByTestAndTaskDescriptionAndMaxPoints(test, description, points)) {
				throw new Exception("Student you want to create already exists!");
			}
			else {
				TestTask newTask = new TestTask(test, description, points);
				taskRepo.save(newTask);
			}
		}
		
		//------------------------------------------------------------------------------------
		//--------------RETRIEVE--------------------------------------------------------------
		@Override 
		public TestTask retrieveTaskById(long id) throws Exception{
			if(id < 0) {
				throw new Exception("Choose correct ID!");
			}
			if(!taskRepo.existsById(id)) {
				throw new Exception("Task with ID [ " + id + " ] doesn't exists!");
			}
			TestTask retrievedTask = taskRepo.findById(id).get();
			
			return retrievedTask;
		}
		//------------------------------------------------------------------------------------
		//---------------UPDATE---------------------------------------------------------------
		@Override 
		public void updateTaskById(long id,  long testId, String description, double points) throws Exception{
			TestTask taskForUpdate = retrieveTaskById(id);
			if(description == null || points<=0) {
				throw new Exception ("Incorrect input parameters!");
			}
			CourseTests foundTest = testRepo.findById(testId).orElseThrow(() -> new Exception("Test not found!"));
			if(!taskForUpdate.getTaskDescription().equals(description)) {
				taskForUpdate.setTaskDescription(description);
			}
			if(taskForUpdate.getMaxPoints() != points) {
				taskForUpdate.setMaxPoints(points);
			}
			
			taskRepo.save(taskForUpdate);
		}
		//------------------------------------------------------------------------------------
		
		//--------------DELETE----------------------------------------------------------------

		@Override
		public void deleteTaskById(long id) throws Exception {
		    TestTask task = retrieveTaskById(id);
		    taskRepo.delete(task);
		}

		//--------------------------------------------------------------------------------------
		
		//===========================END OF CRUD==================================================================
		
		@Override 
		public ArrayList<TestTask> selectAllTasksByTest(long testId) throws Exception{
			 
		    CourseTests test = testRepo.findById(testId).orElseThrow(() -> new Exception("Test with ID " + testId + " not found"));

		    ArrayList<TestTask> tasks = taskRepo.findByTest(test);

		    if (tasks.isEmpty()) {
		        throw new Exception("This test has no tasks");
		    }

		    return tasks;
		}
		
		//--------------------------------------------------------------------------------------
		
		@Override
		public List<CourseTests> selectAllTests(){
			return (List<CourseTests>) testRepo.findAll();
		}
		//--------------------------------------------------------------------------------------
		@Override
		public CourseTests getTestById(long testId) throws Exception {
		    return testRepo.findById(testId)
		        .orElseThrow(() -> new Exception("Test not found"));
		}
}
