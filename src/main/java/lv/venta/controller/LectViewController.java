package lv.venta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.model.CourseTests;
import lv.venta.model.Lecturers;
import lv.venta.model.Students;
import lv.venta.model.StudyCourses;
import lv.venta.model.TestResult;
import lv.venta.service.ICourseTestCRUDService;
import lv.venta.service.ICoursesCRUDService;
import lv.venta.service.ILectViewService;

@Controller
@RequestMapping("/professor")
public class LectViewController {
	@Autowired
	private ILectViewService lectService;
	@Autowired
	private ICourseTestCRUDService testService;
	@Autowired
	private ICoursesCRUDService courseService;
	
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
	@GetMapping("/courses/{courseId}/tests") //localhost:8081/professor/courses/1/tests
	public String getControllerCoursesTests(@PathVariable(name = "courseId") long courseId, Model model) {
		try {
			List<CourseTests> allTests = lectService.allTestsByCourse(courseId);
			System.out.println("Atrasti testi: " + allTests.size());
			model.addAttribute("allTests", allTests);
			model.addAttribute("courseId", courseId);
		    return "lecturers-courseTests";
	    
		}catch(Exception e){
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		
	}
	//-------------------------------------------
	//-----------AllStudentsOfTheTest---------------
	@GetMapping("/courses/{courseId}/tests/{testId}/students") //localhost:8081/professor/courses/1/tests/1
	public String getControllerCoursesTestsStudents(@PathVariable(name = "courseId") long courseId,@PathVariable(name = "testId") long testId, Model model) {
		try {
			List<Students> allStudents = lectService.allStudentsOfTest(testId);
			System.out.println("Atrasti studenti: " + allStudents.size());
			model.addAttribute("students", allStudents);
			return "students-all";
//			List<TestResult> allResults = lectService.allResultsOfTheTest(testId);
//			model.addAttribute("results", allResults);
//		    return "lecturers-TestsResults";
	    
		}catch(Exception e){
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		
	}
	//----------------------------------------------
	//-------Adding new Test to the course----------
	@GetMapping("/courses/{courseId}/tests/add") //localhost:8081/professor/courses/1/tests/add
	public String getControllerAddNewCourseTest(@PathVariable(name ="courseId") long courseId, Model model) {
		
		try {
			StudyCourses courseFind = courseService.retrieveCourseById(courseId);
			System.out.println("Atrasts kurss: " + courseFind);
		    
			model.addAttribute("courseTest", new CourseTests());
			model.addAttribute("course", courseFind);
			return "lecturers-create-courseTest";
		} catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
	    
	}
	
	
	@PostMapping("/courses/{courseId}/tests/add")
	public String postConstrollerAddNewCourseTest(@PathVariable(name ="courseId") long courseId, CourseTests courseTest, BindingResult result, Model model) {
	    try {
	        StudyCourses courseFind = courseService.retrieveCourseById(courseId);
	        courseTest.setCourse(courseFind); 

	        
	        if (result.hasErrors()) {
	            model.addAttribute("course", courseFind);
	            return "lecturers-create-courseTest";
	        }

	        testService.createCourseTest(courseTest.getTestTitle(), courseTest.getTestDescription(), courseTest.getPoints(), courseFind);
	        return "redirect:/professor/courses/" + courseId + "/tests";
	    } catch(Exception e) {
	        model.addAttribute("package", e.getMessage());
	        return "show-error";
	    }
	}
		
	
	//----------------------------------------------
	
}
