package lv.venta.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

	private IStudentViewService studentService;
	private ILectViewService lectService;
	private ITestResultCRUDService resultService;
	private ICourseTestCRUDService testService;
	public StudentViewController (IStudentViewService studentService, ILectViewService lectService, ITestResultCRUDService resultService, ICourseTestCRUDService testService) 
		{this.studentService = studentService;
		this.lectService = lectService;
		this.resultService = resultService;
		this.testService = testService;}

	
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
			if (testFind.getDeadline() != null && LocalDateTime.now().isAfter(testFind.getDeadline())) {
			    throw new Exception("Deadline is over. You cannot upload files anymore.");
			}
			
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
	//------------FilesSending------------------
	@PostMapping("/courses/{courseId}/tests/{testId}/upload")
	public String uploadZip(@PathVariable long testId, @PathVariable long courseId, 
	                        @RequestParam("file") MultipartFile file,
	                        Authentication auth,
	                        Model model) {
	    try {
	    	model.addAttribute("courseId", courseId);
	    	model.addAttribute("testId", testId);
	        studentService.uploadZip(testId, file, auth.getName());
	    	return "redirect:/student/courses/" + courseId + "/tests";
	    } catch (Exception e) {
	        model.addAttribute("package", e.getMessage());
	        return "show-error";
	    }
	}
	//------------------------------------------
	

}
