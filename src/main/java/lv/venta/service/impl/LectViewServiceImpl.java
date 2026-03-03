package lv.venta.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lv.venta.model.CourseTests;
import lv.venta.model.Lecturers;
import lv.venta.model.MyUser;
import lv.venta.model.Students;
import lv.venta.model.StudyCourses;
import lv.venta.model.TestResult;
import lv.venta.model.TestTask;
import lv.venta.repo.ICourseTestRepo;
import lv.venta.repo.ILecturersRepo;
import lv.venta.repo.IMyUserRepo;
import lv.venta.repo.IStudyCourseRepo;
import lv.venta.service.ILectViewService;

@Service
public class LectViewServiceImpl implements ILectViewService{
	@Autowired
	private ILecturersRepo lecturersRepo;
	@Autowired 
	private IStudyCourseRepo coursesRepo;
	@Autowired
	private ICourseTestRepo testRepo;
	@Autowired
	private IMyUserRepo userRepo;
	
	
	@Override
	public List<StudyCourses> allCoursesForLecturer(long lectId) throws Exception{
		Lecturers lecturer = lecturersRepo.findById(lectId)
	        .orElseThrow(() -> new Exception("Lecturer not found!"));
	
	    List<StudyCourses> courses = coursesRepo.findByLecturers(lecturer);
	
//	    if(courses.isEmpty())
//	        throw new Exception("This lecturer has no courses");
	
	    return courses;
	}
	
	@Override
	public List<CourseTests> allTestsByCourse(long courseId) throws Exception{
		StudyCourses course = coursesRepo.findById(courseId)
				.orElseThrow(()-> new Exception("Course not found!"));
		List<CourseTests> tests = testRepo.findByCourse(course);
//		if(tests.isEmpty()) {
//			throw new Exception("This course has no tests!");
//		}
		return tests;
		
	}
	
	@Transactional
	@Override
	public List<Students> allStudentsOfTest(long testId) throws Exception{
		CourseTests test = testRepo.findById(testId)
				.orElseThrow(()-> new Exception("Test not found!"));
		List<Students> students = new ArrayList<>();
		List<TestTask> tasks = test.getTasksForTest();
		List<TestResult> results = new ArrayList<>();
		
		for(TestTask tt : tasks){
			results = tt.getTestResults();
			for(TestResult tr : results) {
				Students foundedStudent = tr.getStudentProgramCourse().getStudentProgram().getStudent();
				if(!students.contains(foundedStudent)) {
					students.add(foundedStudent);
				}
			}
		}
//		if (students.isEmpty()) {
//	        throw new Exception("No students have taken this test");
//	    }
		
		return students;
	}
	
	@Transactional
	@Override
	public List<TestResult> allResultsOfTheTest(long testId) throws Exception{
		CourseTests test = testRepo.findById(testId)
				.orElseThrow(()-> new Exception("Test not found!"));
		List<TestResult> results = new ArrayList<>();
		List<TestTask> tasks = test.getTasksForTest();
		for(TestTask tt : tasks){
			results = tt.getTestResults();
		}
		if (results.isEmpty()) {
	        throw new Exception("This test has no results!");
	    }
		return results;
	}
	
	@Override
	public Lecturers getAuthorisedId() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = auth.getName();

	    MyUser user = userRepo.findByUsername(currentUsername);

	    if (user != null && user.getLecturer() != null) {
	        return user.getLecturer();
	    }
	    
	    return null;
	}
	
	@Override
	public double testPointsCounter(long testId) throws Exception{
		CourseTests test = testRepo.findById(testId)
				.orElseThrow(()-> new Exception("Test not found!"));
		List<TestTask> tasks = test.getTasksForTest();
		double counter = 0;
		for(TestTask t : tasks) {
			counter += t.getMaxPoints();
		}
		return counter;
	}
	//Update testResult can be taken from CRUD service
	//Adding new tests to the course can be taken from courseTestCRUD service

	
	
	
}
