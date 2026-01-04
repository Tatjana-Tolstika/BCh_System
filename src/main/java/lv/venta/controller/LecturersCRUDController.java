package lv.venta.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.model.Lecturers;
import lv.venta.service.ILecturersCRUDService;

@Controller
@RequestMapping("/lecturers/crud")
public class LecturersCRUDController {
	@Autowired 
	private ILecturersCRUDService lecturersService;
	
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
