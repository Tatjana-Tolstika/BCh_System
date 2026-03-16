package lv.venta.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.venta.model.StudyCourses;
import lv.venta.service.ICoursesCRUDService;

@Controller
@RequestMapping("/admin/courses/crud")
public class CoursesCRUDController {
	@Autowired 
	private ICoursesCRUDService coursesService;
	
	//----------------------ADD---------------------------------------
			@GetMapping("/add") //localhost:8081/courses/crud/add
			public String getControllerAddNewCourse(Model model) {
				model.addAttribute("courses", new StudyCourses());
				return "create-course";
			}
			
			@PostMapping("/add")
			public String postConstrollerAddNewCourse(@Valid StudyCourses course, BindingResult result, Model model) {
				if(result.hasErrors()) {
					return "create-course";
				}
				try {
					coursesService.createCourse(course.getCourseTitle(), course.getCredits());
				}
				catch(Exception e) {
					model.addAttribute("package", e.getMessage());
					return "show-error";
				}
				return "redirect:/courses/crud/all";
			}
			//----------------------------------------------------------------
			
			//-------------------DELETE-------------------------------------------------------------------------------
			@GetMapping("/delete/{id}")//localhost:8081/lecturers/crud/delete/2
			public String getControllerDeleteCourseById(@PathVariable(name = "id") long id, Model model)
			{
				try {
					coursesService.deleteCourse(id);
					model.addAttribute("package", coursesService.selectAllCourses());
					return "redirect:/courses/crud/all";
					
				} catch (Exception e) {
					model.addAttribute("package", e.getMessage());
					return "show-error";
				}
				
			}
		
			//----------------------------------------------------------------
			//------------------UPDATE----------------------------------------
			@GetMapping("/update/{id}") //localhost:8081/courses/crud/update/3
			public String getControllerUpdateCourseById(@PathVariable(name = "id") long id, Model model) {
				try {
				StudyCourses courseToUpdate = coursesService.retrieveCourseById(id);
				model.addAttribute("course", courseToUpdate);
				return "update-course";
				}
				catch (Exception e) {
					model.addAttribute("package", e.getMessage());
					return "show-error";
				}
			}
			
			@PostMapping("/update/{id}")
			public String postConstrollerUpdateCourseById(@PathVariable(name = "id") long id, @Valid StudyCourses course, BindingResult result, Model model) {
				if(result.hasErrors()) {
					try{
						return "update-course";
					}catch (Exception e) {
						model.addAttribute("package", e.getMessage());
						return "show-error";
					}
				}
				
				try {
					coursesService.updateCourseById(id, course.getCourseTitle(), course.getCredits());
					return "redirect:/courses/crud/all";
				}
				catch(Exception e) {
					model.addAttribute("package", e.getMessage());
					return "show-error";
				}
				
				
			}
			//-------------------------------------------------------------------------------------
		@GetMapping("/all") //localhost:8081/courses/crud/all
		public String getConstrollerGetAllCourses(Model model) {
			try {
				ArrayList<StudyCourses> allCourses = coursesService.selectAllCourses();
				model.addAttribute("courses", allCourses);
				return "courses-all";
			}
			catch(Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error";
			}
		}
}
