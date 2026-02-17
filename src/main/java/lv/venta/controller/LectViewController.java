package lv.venta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.model.CourseTests;
import lv.venta.model.Lecturers;
import lv.venta.model.StudyCourses;
import lv.venta.service.ILectViewService;

@Controller
@RequestMapping("/professor")
public class LectViewController {
	@Autowired
	private ILectViewService lectService;
	
	//-------------AllCourses-------------------
	@GetMapping("/courses") //localhost:8081/professor/courses
	public String getControllerAllProfessorCourses(Model model) {
		try {
		Lecturers foundedLector = lectService.getAuthorisedId();
		List<StudyCourses> allCourses = lectService.allCoursesForLecturer(foundedLector.getLecturersId());
	    System.out.println("Atrasti kursi: " + allCourses.size());
	    model.addAttribute("courses", allCourses);
	    return "lecturers-courses";
	    
		}catch(Exception e){
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		
	}
	//------------------------------------------
	//-------------AllTestsByCourse-------------
	@GetMapping("/courses/{courseId}/tests") //localhost:8081/professor/courses
	public String getControllerCoursesTests(@PathVariable(name = "courseId") long courseId, Model model) {
		try {
			List<CourseTests> allTests = lectService.allTestsByCourse(courseId);
			System.out.println("Atrasti testi: " + allTests.size());
			model.addAttribute("allTests", allTests);
		    return "lecturers-courseTests";
	    
		}catch(Exception e){
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		
	}
	//-------------------------------------------
	
}
