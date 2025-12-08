package lv.venta.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
			studentsService.CreateStudent(student.getStudentName(), student.getStudentSurname(), student.getMatriculationNr(), student.getEmail());
		}
		catch(Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		return "redirect:/students/crud/all";
	}
	//----------------------------------------------------------------
}
