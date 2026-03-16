package lv.venta.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.model.CourseTests;
import lv.venta.model.Students;
import lv.venta.model.StudyCourses;
import lv.venta.model.TestResult;
import lv.venta.service.ICourseTestCRUDService;
import lv.venta.service.ILectViewService;
import lv.venta.service.IStudentViewService;
import lv.venta.service.ITestResultCRUDService;

@Controller
@RequestMapping("/student")
public class StudentViewController {
	@Autowired
	private IStudentViewService studentService;
	@Autowired
	private ILectViewService lectService;
	@Autowired
	private ITestResultCRUDService resultService;
	@Autowired
	private ICourseTestCRUDService testService;
	
	//-------------AllCourses-------------------
	@GetMapping("/courses") //localhost:8081/student/courses
	public String getControllerAllStudentCourses(Model model) {
		try {
		Students studentFind = studentService.getAuthorisedId();
		List<StudyCourses> allCourses = studentService.coursesForStudent(studentFind.getStudentId());
	    System.out.println("Atrasti kursi: " + allCourses.size());
	    model.addAttribute("courses", allCourses);
	    return "student-courses";
	    
		}catch(Exception e){
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		
	}
	//------------------------------------------
	//-------------AllTestsByCourse-------------
	@GetMapping("/courses/{courseId}/tests") //localhost:8081/student/courses/1/tests
	public String getControllerCoursesTests(@PathVariable(name = "courseId") long courseId, Model model) {
		try {
			List<CourseTests> allTests = studentService.allTestsByCourseAndStatus(courseId);
			System.out.println("Atrasti testi: " + allTests.size());
			
			Students studentFind = studentService.getAuthorisedId();
			//List<Double> allResults = new ArrayList<>();
			Map<Long, Double> allResults = new HashMap<Long, Double>();
			for(CourseTests ct: allTests) {
				allResults.put(ct.getTestId(), lectService.getStudentResult(ct.getTestId(), studentFind.getStudentId()));
			}
			model.addAttribute("results", allResults);
			model.addAttribute("allTests", allTests);
			model.addAttribute("courseId", courseId);
		    return "student-courseTests";
	    
		}catch(Exception e){
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		
	}
	//-------------------------------------------
	//-------------AllCourses-------------------
	@GetMapping("/courses/{courseId}/tests/{testId}/results") //localhost:8081/student/courses/1/tests/1/results
	public String getControllerTestsDetailedResults(@PathVariable(name = "courseId") long courseId,@PathVariable(name = "testId") long testId, Model model) {
		try {
			Students studentFind = studentService.getAuthorisedId();
			System.out.println("Atrasts students: " + studentFind.getStudentName()+ studentFind.getStudentSurname());
			
			CourseTests testFind = testService.retrieveTestById(testId);
			
			List<TestResult> results = resultService.selectResultByTestAndStudentId(testId, studentFind.getStudentId());
			model.addAttribute("studentResults", results);
			model.addAttribute("courseId", courseId);
			model.addAttribute("testId", testId);
			model.addAttribute("studentId", studentFind.getStudentId());
			model.addAttribute("testFind", testFind);
			return "student-testTaskResults";
		} catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		
	}
	//------------------------------------------
	

}
