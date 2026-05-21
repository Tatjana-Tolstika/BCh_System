package lv.venta.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.venta.model.StudentProgramCourse;
import lv.venta.model.StudyCourses;
import lv.venta.model.TestResult;
import lv.venta.model.TestTask;
import lv.venta.service.ICourseTestCRUDService;
import lv.venta.service.IStudentProgramCourseCRUDService;
import lv.venta.service.ITestResultCRUDService;
import lv.venta.service.ITestTaskCRUDService;

@Controller
@RequestMapping("/admin/testResult/crud")
public class TestResultCRUDController {
	

	private ITestResultCRUDService resultService;
	private IStudentProgramCourseCRUDService spcService;
	private ITestTaskCRUDService taskService;
	private ICourseTestCRUDService testService;
	public TestResultCRUDController (ITestResultCRUDService resultService, IStudentProgramCourseCRUDService spcService, ITestTaskCRUDService taskService, ICourseTestCRUDService testService) 
		{this.resultService = resultService;
		this.spcService = spcService;
		this.taskService = taskService;
		this.testService = testService;}

	
	
	//----------------------ADD---------------------------------------
	@GetMapping("/add/{testId}")//localhost:8081/testResult/crud/add/1
    public String getControllerAddNewResult(@PathVariable(name = "testId") long testId, Model model) {
        try {
        	
        	TestResult result = new TestResult();
        	StudyCourses course = testService.selectCourseByTest(testId);
        	List<TestTask> allTasks = taskService.selectAllTasksByTest(testId);
        	List<StudentProgramCourse> allSPCOfCourse = spcService.selectStudentByCourse(course.getCourseId());
        	System.out.println("Atrastas StudentProgramCourse: " + allSPCOfCourse.size());
        	model.addAttribute("studentProgramCourse", allSPCOfCourse);
        	model.addAttribute("foundedCourse", course);
        	model.addAttribute("testResult", result);
        	model.addAttribute("tasks", allTasks);
        	model.addAttribute("testId", testId);

            return "create-testResult";

        } catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "show-error";
        }
    }
	
	@PostMapping("/add/{testId}")
	public String postControllerAddNewResult( @PathVariable long testId, @Valid TestResult testResult, BindingResult result, Model model) {

	    try {
	        if (result.hasErrors()) {
	            StudyCourses course = testService.selectCourseByTest(testId);
	            model.addAttribute("studentProgramCourse", spcService.selectStudentByCourse(course.getCourseId()));
	            model.addAttribute("tasks", taskService.selectAllTasksByTest(testId));
	            model.addAttribute("foundedCourse", course);
	            return "create-testResult";
	        }

	        resultService.createResult( testResult.getComments(),testResult.getMinus(), testResult.getTask(), testResult.getStudentProgramCourse());

	        return "redirect:/testResult/crud/all";

	    } catch (Exception e) {
	        model.addAttribute("package", e.getMessage());
	        return "show-error";
	    }
	}
		
		//----------------------------------------------------------------
	//-------------------DELETE-------------------------------------------------------------------------------
		@GetMapping("/delete/{resultId}")//localhost:8081/testResult/crud/delete/1
		public String getControllerDeleteResultById(@PathVariable(name = "resultId") long resultId, Model model)
		{
			try {
				resultService.deleteResultById(resultId);
				model.addAttribute("package", resultService.selectAllResults());
				return "redirect:/testResult/crud/all";
				
			} catch (Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error";
			}
			
		}
		
		//------------------UPDATE----------------------------------------
		@GetMapping("/update/{resultId}")//localhost:8081/testResult/crud/update/3
		public String getControllerUpdateResultById(@PathVariable(name = "resultId") long resultId, Model model) {
		    try {
		        TestResult resultForUpdate = resultService.retrieveResultById(resultId);
		        
		        if (resultForUpdate.getTask() == null) {
		        	resultForUpdate.setTask(new TestTask());
		        }
		        if (resultForUpdate.getStudentProgramCourse() == null) {
		        	resultForUpdate.setStudentProgramCourse(new StudentProgramCourse());
		        }
		        
		        model.addAttribute("taskMaxPoints", resultForUpdate.getTask().getMaxPoints());
		        model.addAttribute("testResult", resultForUpdate);
		        return "update-testResult";
		    } catch (Exception e) {
		        model.addAttribute("package", e.getMessage());
		        return "show-error";
		    }
		}
		
		@PostMapping("/update/{resultId}")
		public String postConstrollerUpdateResultById(@PathVariable(name = "resultId") long resultId, @Valid TestResult testResult, BindingResult result,Model model) {
			
			System.out.println(resultId);
		    System.out.println(testResult);
		    
		    try {
		        if (result.hasErrors()) {
		            TestResult original = resultService.retrieveResultById(resultId);
		            testResult.setTask(original.getTask());
		            testResult.setStudentProgramCourse(original.getStudentProgramCourse());
		            
		            model.addAttribute("testResult", testResult); 
		            model.addAttribute("taskMaxPoints", original.getTask().getMaxPoints());
		            model.addAttribute("resultId", resultId);
		            return "update-testResult";
		        }
		        
		        TestResult currentResult = resultService.retrieveResultById(resultId);
		        long spcId = currentResult.getStudentProgramCourse().getStudentProgramCourseId();
		        long taskId = currentResult.getTask().getTaskId();
		        
		        resultService.updateTestResultById(resultId, spcId, taskId, testResult.getComments(), testResult.getMinus());
		        return "redirect:/testResult/crud/all";
		        
		    } catch(Exception e) {
		        model.addAttribute("package", e.getMessage());
		        return "show-error";
		    }
			
			
		}

		//-------------------------------------------------------------------------------------
	//----------------------------------------------------------------
	
	@GetMapping("/all") //localhost:8081/testResult/crud/all
	public String getControllerGetAllResults(Model model) {
		try {
			List<TestResult> allResults = resultService.selectAllResults();
			model.addAttribute("results", allResults);
			return "testResults-all";
		}
		catch(Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
	}
	
	
}
