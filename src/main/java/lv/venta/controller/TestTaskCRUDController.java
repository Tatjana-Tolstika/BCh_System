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
import lv.venta.model.CourseTests;
import lv.venta.model.TestTask;
import lv.venta.service.ITestTaskCRUDService;

@Controller
@RequestMapping("/admin/testTask/crud")
public class TestTaskCRUDController {

	private ITestTaskCRUDService taskService;
	public TestTaskCRUDController (ITestTaskCRUDService taskService) {this.taskService = taskService;}
	
	
	//----------------------ADD---------------------------------------
		@GetMapping("/add/{testId}") //localhost:8081/testTask/crud/add
		public String getControllerAddNewTestTask(@PathVariable(name = "testId") long testId, Model model) {
			
			try {
		        CourseTests test = taskService.getTestById(testId);

		        TestTask task = new TestTask();
		        task.setTest(test);

		        model.addAttribute("testTask", task);
		        model.addAttribute("test", test); 

		        return "create-TestTask";

		    } catch (Exception e) {
		        model.addAttribute("package", e.getMessage());
		        return "show-error";
		    }
		}
		
		@PostMapping("/add/{testId}")
		public String postConstrollerAddNewTestTask(@PathVariable(name = "testId") long testId, @Valid TestTask task, BindingResult result, Model model) {
			
			try {
				if (result.hasErrors()) {
			        model.addAttribute("test", taskService.getTestById(testId));
			        model.addAttribute("fixedId", testId);
			        return "create-TestTask";
			    }
				CourseTests test = taskService.getTestById(testId);
				taskService.createTask(test, task.getTaskDescription(), task.getMaxPoints(), task.getTaskNotes());
				return "redirect:/testTask/crud/all/" + testId;
			}
			catch(Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error";
			}
			
		}
		//----------------------------------------------------------------
		//-------------------DELETE-------------------------------------------------------------------------------
		@GetMapping("/delete/{testId}/{taskId}")//localhost:8081/testTask/crud/delete/1/3
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
		@GetMapping("/update/{testId}/{taskId}") //localhost:8081/testTask/crud/update/1/3
		public String getControllerUpdateTaskById(@PathVariable(name = "testId") long testId,@PathVariable(name = "taskId") long taskId, Model model) {
			try {
			TestTask taskToUpdate = taskService.retrieveTaskById(taskId);
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
			
			try {
				if (result.hasErrors()) {
					CourseTests currentTest = taskService.getTestById(testId);
		            model.addAttribute("testName", currentTest);
					model.addAttribute("testTask", task); 
					model.addAttribute("tests", taskService.selectAllTests());
				  return "update-testTask";
				}
				taskService.updateTaskById(taskId, testId, task.getTaskDescription(), task.getMaxPoints(), task.getTaskNotes());
				return "redirect:/testTask/crud/all/" + testId;
			}
			catch(Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error";
			}
			
		}

		//-------------------------------------------------------------------------------------
		
		@GetMapping("/all/{testId}") //localhost:8081/testTask/crud/all/1
		public String getConstrollerGetAllTests(Model model, @PathVariable(name = "testId") long testId) {
			try {
				List<TestTask> allTasks = taskService.selectAllTasksByTest(testId);
				model.addAttribute("testTasks", allTasks);
				return "testTasks-all";
			}
			catch(Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error";
			}
		}
}
