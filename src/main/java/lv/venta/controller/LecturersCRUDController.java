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
		public String postConstrollerAddNewLecturer(@Valid Lecturers lecturer, BindingResult result, Model model) {
			if(result.hasErrors()) {
				return "create-lecturer";
			}
			try {
				lecturersService.createLecturer(lecturer.getLecturerUsername(),lecturer.getLecturerName(), lecturer.getLecturerSurname(), lecturer.getLecturerDegree());
			}
			catch(Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error";
			}
			return "redirect:/lecturers/crud/all";
		}
		//----------------------------------------------------------------
		
		//-------------------DELETE-------------------------------------------------------------------------------
		@GetMapping("/delete/{id}")//localhost:8081/lecturers/crud/delete/3
		public String getControllerDeleteLecturerById(@PathVariable(name = "id") long id, Model model)
		{
			try {
				lecturersService.deleteLecturer(id);
				model.addAttribute("package", lecturersService.selectAllLecturers());
				return "redirect:/lecturers/crud/all";
				
			} catch (Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error";
			}
			
		}
	
		//----------------------------------------------------------------
		//------------------UPDATE----------------------------------------
		@GetMapping("/update/{id}") //localhost:8081/lecturers/crud/update/5
		public String getControllerUpdateLecturerById(@PathVariable(name = "id") long id, Model model) {
			try {
			Lecturers lecturerToUpdate = lecturersService.retrieveLecturerById(id);
			model.addAttribute("lecturer", lecturerToUpdate);
			return "update-lecturer";
			}
			catch (Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error";
			}
		}
		
		@PostMapping("/update/{id}")
		public String postConstrollerUpdateLecturerById(@PathVariable(name = "id") long id, @Valid Lecturers lecturer, BindingResult result, Model model) {
			if(result.hasErrors()) {
				try{
					return "update-lecturer";
				}catch (Exception e) {
					model.addAttribute("package", e.getMessage());
					return "show-error";
				}
			}
			
			try {
				lecturersService.updateLecturerById(id,lecturer.getLecturerUsername(), lecturer.getLecturerName(), lecturer.getLecturerSurname(), lecturer.getLecturerDegree());
				return "redirect:/lecturers/crud/all";
			}
			catch(Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error";
			}
			
			
		}
		//-------------------------------------------------------------------------------------
	@GetMapping("/all") //localhost:8081/lecturers/crud/all
	public String getConstrollerGetAllLecturers(Model model) {
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

