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
import lv.venta.model.Lecturers;
import lv.venta.service.ILecturersCRUDService;

@Controller
@RequestMapping("/lecturers/crud")
public class LecturersCRUDController {
	@Autowired 
	private ILecturersCRUDService lecturersService;
	
	//----------------------ADD---------------------------------------
		@GetMapping("/add") //localhost:8081/lecturers/crud/add
		public String getControllerAddNewLecturer(Model model) {
			model.addAttribute("lecturers", new Lecturers());
			return "create-lecturer";
		}
		
		@PostMapping("/add")
		public String postConstrollerAddNewStudent(@Valid Lecturers lecturer, BindingResult result, Model model) {
			if(result.hasErrors()) {
				return "create-lecturer";
			}
			try {
				lecturersService.CreateLecturer(lecturer.getLecturerName(), lecturer.getLecturerSurname(), lecturer.getLecturerDegree());
			}
			catch(Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error";
			}
			return "redirect:/lecturers/crud/all";
		}
		//----------------------------------------------------------------
	
	@GetMapping("/all") //localhost:8081/lecturers/crud/all
	public String getConstrollerGetAllStudents(Model model) {
		try {
			ArrayList<Lecturers> allLecturers = lecturersService.selectAllLecturers();
			model.addAttribute("lecturers", allLecturers);
			return "lecturers-all";
		}
		catch(Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
	}
}
//======================================CRUD=======================================================
	//-------------------------------CREATE-----------------------------------
	
	//-----------------------------------------------------------------------
	//------------------------------DELETE---------------------------------
	//-----------------------------------------------------------------------
	//-----------------------------RETRIEVE-----------------------------------
	//-----------------------------------------------------------------------
	//--------------------------UPDATE---------------------------------------
	//-----------------------------------------------------------------------
	
	//=================================================================================================
