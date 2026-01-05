package lv.venta.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.StudyCourses;
import lv.venta.repo.IStudyCourseRepo;
import lv.venta.service.ICoursesCRUDService;

@Service
public class CoursesCRUDServiceImpl implements ICoursesCRUDService{
@Autowired 
private IStudyCourseRepo courseRepo;

//========================CRUD=============================================
	//----------------CREATE-------------------------------------------------------------
		@Override
		public void createCourse(String title, int credits ) throws Exception {
			if(title == null || credits <= 0 || credits > 10) {
				throw new Exception("Incorrect input parameters!");
			}
			
			if(courseRepo.existsByCourseTitleAndCredits(title, credits)) {
				throw new Exception("Course you want to create already exists!");
			}
			else {
				StudyCourses newCourse = new StudyCourses(title, credits);
				courseRepo.save(newCourse);
			}
		}
	//------------------------------------------------------------------------------------
	//--------------RETRIEVE--------------------------------------------------------------
		@Override 
		public StudyCourses retrieveCourseById(long id) throws Exception{
			if(id < 0) {
				throw new Exception("Choose correect ID!");
			}
			if(!courseRepo.existsById(id)) {
				throw new Exception("Course with ID [ " + id + " ] doesn't exists!");
			}
			StudyCourses retrievedCourse = courseRepo.findById(id).get();
			
			return retrievedCourse;
		}
	//------------------------------------------------------------------------------------
	//---------------UPDATE---------------------------------------------------------------
		@Override 
		public void updateCourseById(long id, String title, int credits) throws Exception{
			StudyCourses courseForUpdate = retrieveCourseById(id);
			if(title == null || credits <=0 || credits > 10) {
				throw new Exception ("Incorrect input parameters!");
			}
			if(courseForUpdate.getCourseTitle() != title) {
				courseForUpdate.setCourseTitle(title);
			}
			if(courseForUpdate.getCredits() != credits) {
				courseForUpdate.setCredits(credits);
			}
			
			
			courseRepo.save(courseForUpdate);
		}
	//------------------------------------------------------------------------------------
	//--------------DELETE----------------------------------------------------------------
		@Override 
		public void deleteCourse(long id)throws Exception{
			StudyCourses course = retrieveCourseById(id);
			courseRepo.delete(course);
		}
	//--------------------------------------------------------------------------------------
		
		@Override 
		public ArrayList<StudyCourses> selectAllCourses() throws Exception{
			if(courseRepo.count() == 0) {
				throw new Exception("Course list is empty!");
			}
			ArrayList<StudyCourses> result = (ArrayList<StudyCourses>) courseRepo.findAll();
			return result;
		}
	//=========================================================================
}
