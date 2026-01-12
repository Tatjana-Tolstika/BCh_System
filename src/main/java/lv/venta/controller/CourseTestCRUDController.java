package lv.venta.controller;

import java.util.ArrayList;
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
import lv.venta.model.StudyCourses;
import lv.venta.service.ICourseTestCRUDService;

@Controller
@RequestMapping("/courseTests/crud")
public class CourseTestCRUDController {
	@Autowired 
	private ICourseTestCRUDService testService;

	
	//----------------------ADD---------------------------------------
	@GetMapping("/add") //localhost:8081/courseTests/crud/add
	public String getControllerAddNewCourseTest(Model model) {
		List<StudyCourses> allCourses = testService.selectAllCourse();
	    System.out.println("Atrasti kursi: " + allCourses.size());
	    
		model.addAttribute("courseTest", new CourseTests());
		model.addAttribute("courses", allCourses);
		return "create-courseTest";
	}
	
	@PostMapping("/add")
	public String postConstrollerAddNewCourseTest(@Valid CourseTests test, BindingResult result, Model model) {
		if (result.hasErrors()) {
	        model.addAttribute("courses", testService.selectAllCourse());
	        return "create-courseTest";
	    }
		try {
			testService.createCourseTest(test.getTestTitle(), test.getTestDescription(), test.getPoints(), test.getCourse());
			return "redirect:/courseTests/crud/all";
		}
		catch(Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		
	}
	//----------------------------------------------------------------
	
	//-------------------DELETE-------------------------------------------------------------------------------
	@GetMapping("/delete/{id}")//localhost:8081/courseTests/crud/delete/2
	public String getControllerDeleteTestById(@PathVariable(name = "id") long id, Model model)
	{
		try {
			testService.deleteTest(id);
			model.addAttribute("package", testService.selectAllTests());
			return "redirect:/courseTests/crud/all";
			
		} catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		
	}

	//----------------------------------------------------------------
	//------------------UPDATE----------------------------------------
	@GetMapping("/update/{id}") //localhost:8081/courseTests/crud/update/3
	public String getControllerUpdateTestById(@PathVariable(name = "id") long id, Model model) {
		try {
		CourseTests testToUpdate = testService.retrieveTestById(id);
		model.addAttribute("courses", testService.selectAllCourse());
		model.addAttribute("courseTest", testToUpdate);
		model.addAttribute("courseName", testToUpdate.getCourse());
		return "update-courseTest";
		}
		catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
	}
	
	@PostMapping("/update/{id}")
	public String postConstrollerUpdateTestById(@PathVariable(name = "id") long id, @Valid CourseTests test, BindingResult result,Model model) {
		System.out.println(id);
		System.out.println(test);
		if (result.hasErrors()) {
			model.addAttribute("courseTest", test); 
		model.addAttribute("courses", testService.selectAllCourse());
		  return "update-courseTest";
		}
		
		try {
			testService.updateTestById(id, test.getTestTitle(), test.getTestDescription(), test.getPoints(),Integer.parseInt(test.getCourse().getCourseTitle()));
			return "redirect:/courseTests/crud/all";
		}
		catch(Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		
		
		
	}

	//-------------------------------------------------------------------------------------
@GetMapping("/all") //localhost:8081/courseTests/crud/all
public String getConstrollerGetAllTests(Model model) {
	try {
		ArrayList<CourseTests> allTests = testService.selectAllTests();
		model.addAttribute("courseTests", allTests);
		return "courseTests-all";
	}
	catch(Exception e) {
		model.addAttribute("package", e.getMessage());
		return "show-error";
	}
}
}
