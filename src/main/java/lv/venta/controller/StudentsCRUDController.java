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
import lv.venta.model.Students;
import lv.venta.service.IStudentsCRUDService;

@Controller
@RequestMapping("/students/crud")
public class StudentsCRUDController {
	@Autowired 
	private IStudentsCRUDService studentsService;
	
	@GetMapping("/all") //localhost:8081/students/crud/all
	public String getConstrollerGetAllStudents(Model model) {
		try {
			ArrayList<Students> allStudents = studentsService.selectAllStudents();
			model.addAttribute("students", allStudents);
			return "students-all";
		}
		catch(Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
	}
	//----------------------ADD---------------------------------------
	@GetMapping("/add") //localhost:8081/students/crud/add
	public String getControllerAddNewStudent(Model model) {
		model.addAttribute("student", new Students());
		return "create-student";
	}
	
	@PostMapping("/add")
	public String postConstrollerAddNewStudent(@Valid Students student, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "create-student";
		}
		try {
			studentsService.createStudent(student.getStudentName(), student.getStudentSurname(), student.getMatriculationNr(), student.getEmail());
		}
		catch(Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		return "redirect:/students/crud/all";
	}
	//----------------------------------------------------------------
	//------------------UPDATE----------------------------------------
	@GetMapping("/update/{id}") //localhost:8081/students/crud/update/5
	public String getControllerUpdateStudentById(@PathVariable(name = "id") long id, Model model) {
		try {
		Students studentToUpdate = studentsService.retrieveById(id);
		model.addAttribute("student", studentToUpdate);
		return "update-student";
		}
		catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
	}
	
	@PostMapping("/update/{id}")
	public String postConstrollerUpdateStudentById(@PathVariable(name = "id") long id, @Valid Students student, BindingResult result, Model model) {
		if(result.hasErrors()) {
			try{
				model.addAttribute("student", student);
				model.addAttribute("id", id);
				return "update-student";
			}catch (Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error";
			}
		}
		
		try {
			studentsService.updateStudentById(id, student.getStudentName(), student.getStudentSurname(), student.getEmail(), student.getMatriculationNr());
			return "redirect:/students/crud/all";
		}
		catch(Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		
		
	}
	//-------------------------------------------------------------------------------------
	
	//-------------------DELETE-------------------------------------------------------------------------------
	@GetMapping("/delete/{id}")//localhost:8081/students/crud/delete/3
	public String getControllerDeleteStudentById(@PathVariable(name = "id") long id, Model model)
	{
		try {
			studentsService.deleteStudentById(id);
			model.addAttribute("package", studentsService.selectAllStudents());
			return "redirect:/students/crud/all";
			
		} catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		
	}
}
