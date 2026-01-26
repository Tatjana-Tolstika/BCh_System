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

import jakarta.validation.Valid;
import lv.venta.model.CourseTests;
import lv.venta.model.TestTask;
import lv.venta.service.ITestTaskCRUDService;

@Controller
@RequestMapping("/testTask/crud")
public class TestTaskCRUDController {
	@Autowired 
	private ITestTaskCRUDService taskService;
	
	//----------------------ADD---------------------------------------
		@GetMapping("/add") //localhost:8081/testTask/crud/add
		public String getControllerAddNewTestTask(Model model) {
			List<CourseTests> allTests = taskService.selectAllTests();
		    System.out.println("Atrasti testi: " + allTests.size()); //Izņemt
		    
			model.addAttribute("testTask", new TestTask());
			model.addAttribute("test", allTests);
			return "create-TestTask";
		}
		
		@PostMapping("/add")
		public String postConstrollerAddNewTestTask(@Valid TestTask task, BindingResult result, Model model) {
			if (result.hasErrors()) {
		        model.addAttribute("test", taskService.selectAllTests());
		        return "create-TestTask";
		    }
			try {
				taskService.createTask(task.getTest(), task.getTaskDescription(), task.getMaxPoints());
				return "redirect:/testTask/crud/all";
			}
			catch(Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error";
			}
			
		}
		//----------------------------------------------------------------
		//-------------------DELETE-------------------------------------------------------------------------------
		@GetMapping("/delete/{testId}/{taskId}")//localhost:8081/testTask/crud/delete/2
		public String getControllerDeleteTaskById(@PathVariable(name = "taskId") long taskId, @PathVariable(name = "testId") long testId, Model model)
		{
			try {
				taskService.deleteTaskById(taskId);
				model.addAttribute("package", taskService.selectAllTasksByTest(testId));
				return "redirect:/testTask/crud/all/" + testId;
				
			} catch (Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error";
			}
			
		}

		//----------------------------------------------------------------
		//------------------UPDATE----------------------------------------
		@GetMapping("/update/{testId}/{taskId}") //localhost:8081/courseTests/crud/update/3
		public String getControllerUpdateTaskById(@PathVariable(name = "testId") long testId,@PathVariable(name = "taskId") long taskId, Model model) {
			try {
			TestTask taskToUpdate = taskService.retrieveTaskById(id);
			model.addAttribute("tests", taskService.selectAllTests());
			model.addAttribute("testTask", taskToUpdate);
			model.addAttribute("testName", taskToUpdate.getTest());
			return "update-testTask";
			}
			catch (Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error";
			}
		}
		
		@PostMapping("/update/{testId}/{taskId}")
		public String postConstrollerUpdateTestById(@PathVariable(name = "testId") long testId,@PathVariable(name = "taskId") long taskId, @Valid TestTask task, BindingResult result,Model model) {
			System.out.println(taskId);
			System.out.println(task);
			if (result.hasErrors()) {
				model.addAttribute("testTask", task); 
			model.addAttribute("tests", taskService.selectAllTests());
			  return "update-testTask";
			}
			
			try {
				taskService.updateTaskById(taskId, Integer.parseInt(task.getTest().getTestTitle()), task.getTaskDescription(), task.getMaxPoints());
				return "redirect:/testTask/crud/all/" + testId;
			}
			catch(Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error";
			}
			
			
			
		}

		//-------------------------------------------------------------------------------------
}
