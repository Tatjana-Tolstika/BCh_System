package lv.venta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lv.venta.model.MyUser;
import lv.venta.model.Students;
import lv.venta.model.StudyCourses;
import lv.venta.model.TestResult;
import lv.venta.repo.IMyUserRepo;
import lv.venta.repo.IStudyCourseRepo;
import lv.venta.repo.ITestResultRepo;
import lv.venta.service.IStudentViewService;
@Service
public class StudentViewServiceImpl implements IStudentViewService{

	@Autowired
	private IStudyCourseRepo courseRepo;
	@Autowired
	private IMyUserRepo userRepo;
	@Autowired
	private ITestResultRepo resultsRepo;
	
	@Override
	public List<StudyCourses> coursesForStudent(long studentId) throws Exception{
		List<StudyCourses> courses = courseRepo.findByStudentProgramCourseStudentProgramStudentStudentId(studentId);
		return courses;
	}
	
	@Override
	public Students getAuthorisedId() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = auth.getName();

	    MyUser user = userRepo.findByUsername(currentUsername);

	    if (user != null && user.getStudent() != null) {
	        return user.getStudent();
	    }
	    
	    return null;
	}
	
	
	@Override
	public List<TestResult> resultsForStudent(long studentId, long testId) throws Exception{

		List<TestResult> results = resultsRepo.findByTaskTestTestIdAndStudentProgramCourseStudentProgramStudentStudentId(testId, studentId);
		return results;
	}
	

}
