package lv.venta.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import lv.venta.model.TestTask;
import lv.venta.service.ICourseTestCRUDService;
import lv.venta.service.ICoursesCRUDService;
import lv.venta.service.ILectViewService;
import lv.venta.service.IStudentsCRUDService;
import lv.venta.service.ITestResultCRUDService;
import lv.venta.service.ITestTaskCRUDService;

@Controller
@RequestMapping("/professor")
public class LectViewController {
	@Autowired
	private ILectViewService lectService;
	@Autowired
	private ICourseTestCRUDService testService;
	@Autowired
	private ICoursesCRUDService courseService;
	@Autowired
	private ITestTaskCRUDService taskService;
	@Autowired
	private ITestResultCRUDService resultService;
	@Autowired
	private IStudentsCRUDService studentService;
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
	@GetMapping("/courses/{courseId}/tests/{testId}/students") //localhost:8081/professor/courses/1/tests/1/students
	public String getControllerCoursesTestsStudents(@PathVariable(name = "courseId") long courseId,@PathVariable(name = "testId") long testId, Model model) {
		try {
			List<Students> allStudents = lectService.allStudentsOfTestResults(testId);
			Map<Long, Double> allResults = new HashMap<Long, Double>(); //https://www.geeksforgeeks.org/java/map-interface-in-java/
			//https://www.geeksforgeeks.org/java/map-get-method-in-java-with-examples/
			for(Students s : allStudents) {
				allResults.put(s.getStudentId(), lectService.getStudentResult(testId, s.getStudentId()));
			}
			System.out.println("Atrasti results: " + allResults);
			System.out.println("Atrasti studenti: " + allStudents.size());
			model.addAttribute("students", allStudents);
			model.addAttribute("results", allResults);
			
			return "lecturers-TestsResults";
	    
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
	//------------All tasks of the test-------------
	@GetMapping("/courses/{courseId}/tests/{testId}/tasks") //localhost:8081/professor/courses/1/tests/1/tasks
	public String getControllerTasksOfTest(@PathVariable(name = "courseId") long courseId,@PathVariable(name = "testId") long testId, Model model) {
		try {
			CourseTests testFind = testService.retrieveTestById(testId);
			
			List<TestTask> allTasks = taskService.selectAllTasksByTest(testId);
			double totalPoints = lectService.testPointsCounter(testId);
			System.out.println("Atrasti taski: " + allTasks.size());
			model.addAttribute("allTasks", allTasks);
			model.addAttribute("totalPoints", totalPoints);
			model.addAttribute("courseId", courseId);
			model.addAttribute("testId", testId);
			model.addAttribute("test", testFind);
		    return "lecturers-courseTestTasks";
	    
		}catch(Exception e){
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		
	}
	//----------------------------------------------
	//-----------Creating new task for the test-----
	@GetMapping("/courses/{courseId}/tests/{testId}/tasks/add") //localhost:8081/professor/courses/1/tests/1/tasks/add
	public String getControllerAddNewTaskForTest(@PathVariable(name ="courseId") long courseId, @PathVariable(name = "testId") long testId,Model model) {
		
		try {
			CourseTests testFind = testService.retrieveTestById(testId);
			System.out.println("Atrasts tests: " + testFind);
		    
			model.addAttribute("testTask", new TestTask());
			model.addAttribute("testFind", testFind);
			return "lecturers-create-testTask";
		} catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
	    
	}
	
	
	@PostMapping("/courses/{courseId}/tests/{testId}/tasks/add")
	public String postControllerAddNewTaskForTest(@PathVariable(name ="courseId") long courseId,@PathVariable(name = "testId") long testId, TestTask testTask, BindingResult result, Model model) {
	    try {
	    	CourseTests testFind = testService.retrieveTestById(testId);
	    	testTask.setTest(testFind); 

	        
	        if (result.hasErrors()) {
	            model.addAttribute("testFind", testFind);
	            return "lecturers-create-testTask";
	        }

	        taskService.createTask(testFind, testTask.getTaskDescription(),testTask.getMaxPoints(), testTask.getTaskNotes());
	        return "redirect:/professor/courses/" + courseId + "/tests/"+ testId +"/tasks";
	    } catch(Exception e) {
	        model.addAttribute("package", e.getMessage());
	        return "show-error";
	    }
	}
	//----------------------------------------------
	
	@GetMapping("/courses/{courseId}/tests/{testId}/students/{studentId}/results")
	public String getControllerStudentAllTasksResults(@PathVariable(name ="courseId") long courseId, @PathVariable(name = "testId") long testId, @PathVariable(name = "studentId") long studentId, Model model) {
		try {
			CourseTests testFind = testService.retrieveTestById(testId);
			System.out.println("Atrasts tests: " + testFind.getTestTitle());
		    
			Students studentFind = studentService.retrieveById(studentId);
			System.out.println("Atrasts students: " + studentFind.getStudentName()+ studentFind.getStudentSurname());
			
			List<TestResult> results = resultService.selectResultByTestAndStudentId(testId, studentId);
			model.addAttribute("studentResults", results);
			model.addAttribute("courseId", courseId);
			model.addAttribute("testId", testId);
			model.addAttribute("studentId", studentId);
			model.addAttribute("studentFind", studentFind);
			return "lecturers-student-results";
		} catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
	}
	
	//------------------------------------------------
	@GetMapping("/courses/{courseId}/tests/{testId}/students/{studentId}/results/{resultId}/update")
	public String getControllerStudentResultUpdateByTask(@PathVariable(name ="courseId") long courseId, @PathVariable(name = "testId") long testId, @PathVariable(name = "studentId") long studentId,
			@PathVariable(name = "resultId") long resultId, Model model) {
		try {
			TestResult resultForUpdate = resultService.retrieveResultById(resultId);

	        model.addAttribute("taskMaxPoints", resultForUpdate.getTask().getMaxPoints());
	        model.addAttribute("studentOfTask",
	                resultForUpdate.getStudentProgramCourse().getStudentProgram().getStudent());
	        model.addAttribute("testResult", resultForUpdate);
	        model.addAttribute("courseId", courseId);
	        model.addAttribute("testId", testId);
	        model.addAttribute("studentId", studentId);
	        model.addAttribute("resultId", resultId);
			return "lecturers-update-testResult";
		} catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
	}
	
	@PostMapping("/courses/{courseId}/tests/{testId}/students/{studentId}/results/{resultId}/update")
	public String postControllerStudentResultUpdateByTask(
	        @PathVariable long courseId,
	        @PathVariable long testId,
	        @PathVariable long studentId,
	        @PathVariable long resultId,
	        TestResult testResult,
	        BindingResult result,
	        Model model) {

	    try {
	        TestResult existingResult = resultService.retrieveResultById(resultId);

	        if (result.hasErrors()) {
	            model.addAttribute("taskMaxPoints", existingResult.getTask().getMaxPoints());
	            model.addAttribute("studentOfTask",
	                    existingResult.getStudentProgramCourse().getStudentProgram().getStudent());
	            model.addAttribute("courseId", courseId);
	            model.addAttribute("testId", testId);
	            model.addAttribute("studentId", studentId);
	            model.addAttribute("resultId", resultId);
	            return "lecturers-update-testResult";
	        }

	        existingResult.setComments(testResult.getComments());
	        existingResult.setMinus(testResult.getMinus());

	        resultService.updateTestResultById(resultId, existingResult.getStudentProgramCourse().getStudentProgramCourseId(), existingResult.getTask().getTaskId() , existingResult.getComments(), existingResult.getMinus());

	        return "redirect:/professor/courses/" + courseId
	                + "/tests/" + testId
	                + "/students/" + studentId
	                + "/results";

	    } catch (Exception e) {
	        model.addAttribute("package", e.getMessage());
	        return "show-error";
	    }
	}
	//--------------------------------------------------------------------------------------------------------------------------
	@PostMapping("/courses/{courseId}/tests/{testId}/status")
	public String toggleTestStatus(@PathVariable long courseId,
	                               @PathVariable long testId,
	                               Model model) {
		try {
	        String message = lectService.controlTestVisibility(testId);

	        if (message != null) {
	            List<CourseTests> allTests = lectService.allTestsByCourse(courseId);
	            model.addAttribute("allTests", allTests);
	            model.addAttribute("courseId", courseId);
	            model.addAttribute("errorMsg", message);
	            return "lecturers-courseTests";
	        }

	        return "redirect:/professor/courses/" + courseId + "/tests";
	        } catch (Exception e) {
	        model.addAttribute("package", e.getMessage());
	        return "show-error";
	    }
	}
	//--------------------------------------------------------------------------------------------------------------------------
	@GetMapping("/courses/{courseId}/tests/{testId}/tasks/{taskId}/update")
	public String getControllerUpdateTask(@PathVariable(name = "courseId") long courseId,
	                                      @PathVariable(name = "testId") long testId,
	                                      @PathVariable(name = "taskId") long taskId,
	                                      Model model) {
	    try {
	        TestTask taskForUpdate = taskService.retrieveTaskById(taskId);

	        model.addAttribute("testTask", taskForUpdate);
	        model.addAttribute("courseId", courseId);
	        model.addAttribute("testId", testId);
	        model.addAttribute("taskId", taskId);

	        return "lecturers-update-testTask";
	    } catch (Exception e) {
	        model.addAttribute("package", e.getMessage());
	        return "show-error";
	    }
	}
	
	@PostMapping("/courses/{courseId}/tests/{testId}/tasks/{taskId}/update")
	public String postControllerUpdateTask(@PathVariable long courseId,
	                                       @PathVariable long testId,
	                                       @PathVariable long taskId,
	                                       TestTask testTask,
	                                       BindingResult result,
	                                       Model model) {
	    try {

	        if (result.hasErrors()) {
	            model.addAttribute("testTask", testTask);
	            model.addAttribute("courseId", courseId);
	            model.addAttribute("testId", testId);
	            model.addAttribute("taskId", taskId);
	            return "lecturers-update-testTask";
	        }

	        taskService.updateTaskById(
	                taskId,
	                testId,
	                testTask.getTaskDescription(),
	                testTask.getMaxPoints(),
	                testTask.getTaskNotes()
	        );

	        return "redirect:/professor/courses/" + courseId + "/tests/" + testId + "/tasks";

	    } catch (Exception e) {
	        model.addAttribute("package", e.getMessage());
	        return "show-error";
	    }
	}
	
}
