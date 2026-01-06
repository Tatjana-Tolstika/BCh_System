package lv.venta.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.CourseTests;
import lv.venta.model.StudyCourses;
import lv.venta.repo.ICourseTestRepo;
import lv.venta.service.ICourseTestCRUDService;

@Service
public class CourseTestCRUDServiceImpl implements ICourseTestCRUDService{
@Autowired
private ICourseTestRepo testRepo;

//========================CRUD=============================================
	//----------------CREATE-------------------------------------------------------------
		@Override
		public void createCourseTest(String title, String description, int points, StudyCourses course ) throws Exception {
			if(title == null || description == null || points <= 0 || course == null ) {
				throw new Exception("Incorrect input parameters!");
			}
			
			if(testRepo.existsByTestTitleAndTestDescriptionAndPointsAndCourse(title, description, points, course)) {
				throw new Exception("Course you want to create already exists!");
			}
			else {
				CourseTests newTest = new CourseTests(title, description, points, course);
				testRepo.save(newTest);
			}
		}
	//------------------------------------------------------------------------------------
	//--------------RETRIEVE--------------------------------------------------------------
		@Override 
		public CourseTests retrieveTestById(long id) throws Exception{
			if(id < 0) {
				throw new Exception("Choose correect ID!");
			}
			if(!testRepo.existsById(id)) {
				throw new Exception("Test with ID [ " + id + " ] doesn't exists!");
			}
			CourseTests retrievedTest = testRepo.findById(id).get();
			
			return retrievedTest;
		}
	//------------------------------------------------------------------------------------
	//---------------UPDATE---------------------------------------------------------------
		@Override 
		public void updateTestById(long id, String title, String description, int points, StudyCourses course) throws Exception{
			CourseTests testForUpdate = retrieveTestById(id);
			if(title == null || description == null || points <= 0 || course == null ) {
				throw new Exception ("Incorrect input parameters!");
			}
			if(!testForUpdate.getTestTitle().equals(title)) {
				testForUpdate.setTestTitle(title);
			}
			if(!testForUpdate.getTestDescription().equals(description)) {
				testForUpdate.setTestDescription(description);
			}
			if(testForUpdate.getPoints() != points) {
				testForUpdate.setPoints(points);
			}
			if(!testForUpdate.getCourse().equals(course)) {
				testForUpdate.setCourse(course);
			}
			
			
			testRepo.save(testForUpdate);
		}
	//------------------------------------------------------------------------------------
	//--------------DELETE----------------------------------------------------------------
		@Override 
		public void deleteTest(long id)throws Exception{
			CourseTests test = retrieveTestById(id);
			testRepo.delete(test);
		}
	//--------------------------------------------------------------------------------------
		
		@Override 
		public ArrayList<CourseTests> selectAllTests() throws Exception{
			if(testRepo.count() == 0) {
				throw new Exception("Tests list is empty!");
			}
			ArrayList<CourseTests> result = (ArrayList<CourseTests>) testRepo.findAll();
			return result;
		}
	//=========================================================================
}
