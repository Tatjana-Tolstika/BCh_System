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
import lv.venta.model.StudyProgram;
import lv.venta.service.IProgramCRUDService;

@Controller
@RequestMapping("/programs/crud")
public class ProgramCRUDController {
	@Autowired 
	private IProgramCRUDService programService;
	
	@GetMapping("/all") //localhost:8081/programs/crud/all
	public String getConstrollerGetAllPrograms(Model model) {
		try {
			ArrayList<StudyProgram> allPrograms = programService.selectAllPrograms();
			model.addAttribute("program", allPrograms);
			return "programs-all";
		}
		catch(Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
	}
	//----------------------ADD---------------------------------------
	@GetMapping("/add") //localhost:8081/programs/crud/add
	public String getControllerAddNewProgram(Model model) {
		model.addAttribute("program", new StudyProgram());
		return "create-program";
	}
			
	@PostMapping("/add")
	public String postConstrollerAddNewProgram(@Valid StudyProgram program, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "create-program";
		}
		try {
			programService.createProgram(program.getProgramTitle(), program.getProgramDegree(), program.getAbbreviation());
		}
		catch(Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		return "redirect:/programs/crud/all";
	}
	//----------------------------------------------------------------
}
