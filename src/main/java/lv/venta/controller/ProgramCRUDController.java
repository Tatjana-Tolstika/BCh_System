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
			programService.createProgram(program.getProgramTitle(), program.getProgramDegree(), program.getAbbreviation(), program.getLength());
		}
		catch(Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		return "redirect:/programs/crud/all";
	}
	//----------------------------------------------------------------
	//------------------UPDATE----------------------------------------
	@GetMapping("/update/{id}") //localhost:8081/programs/crud/update/2
	public String getControllerUpdateProgramById(@PathVariable(name = "id") long id, Model model) {
		try {
		StudyProgram programToUpdate = programService.retrieveProgramById(id);
		model.addAttribute("program", programToUpdate);
		return "update-program";
		}
		catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
	}
		
	@PostMapping("/update/{id}")
	public String postConstrollerUpdateStudentById(@PathVariable(name = "id") long id, @Valid StudyProgram program, BindingResult result, Model model) {
		if(result.hasErrors()) {
			try{
				return "update-program";
			}catch (Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error";
			}
		}
			
		try {
			programService.updateProgramById(id, program.getProgramTitle(), program.getProgramDegree(), program.getAbbreviation(), program.getLength());
			return "redirect:/programs/crud/all";
		}
		catch(Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
			
			
	}
	//-------------------------------------------------------------------------------------
	//-------------------DELETE-------------------------------------------------------------------------------
	@GetMapping("/delete/{id}")//localhost:8081/programs/crud/delete/3
	public String getControllerDeleteProgramById(@PathVariable(name = "id") long id, Model model)
	{
		try {
			programService.deleteProgram(id);
			model.addAttribute("package", programService.selectAllPrograms());
			return "redirect:/programs/crud/all";
				
		} catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
			
	}
	//-------------------------------------------------------------------------------------------
}
