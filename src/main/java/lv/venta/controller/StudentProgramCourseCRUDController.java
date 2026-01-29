package lv.venta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.venta.model.StudentProgram;
import lv.venta.model.StudentProgramCourse;
import lv.venta.model.StudyCourses;
import lv.venta.service.ICoursesCRUDService;
import lv.venta.service.IStudentProgramCRUDService;
import lv.venta.service.IStudentProgramCourseCRUDService;

@Controller
@RequestMapping("/spc/crud")
public class StudentProgramCourseCRUDController {

	@Autowired 
	private IStudentProgramCourseCRUDService spcService;
	@Autowired
	private ICoursesCRUDService coursesService;
	@Autowired
	private IStudentProgramCRUDService spService;
	
	//----------------------ADD---------------------------------------
	@GetMapping("/add") //localhost:8081/spc/crud/add
	public String getControllerAddNewCourseToStudentProgram(Model model) {
		
		try {
			
			List<StudentProgram> allStudentProgram = spService.selectAllStudentProgram();
			List<StudyCourses> allCourses = coursesService.selectAllCourses();
			//Checking
		    System.out.println("Atrastas StudentProgram: " + allStudentProgram.size());
		    System.out.println("Atrasti kursi: " + allCourses.size());
		    
			model.addAttribute("studentProgramCourse", new StudentProgramCourse());
			model.addAttribute("studentPrograms", allStudentProgram); 
			model.addAttribute("courses", allCourses);

	        return "create-studentProgramCourse";

	    } catch (Exception e) {
	        model.addAttribute("package", e.getMessage());
	        return "show-error";
	    }
	}
	
	@PostMapping("/add")
	public String postControllerAddNewCourseToStudentProgram(@Valid StudentProgramCourse spc, BindingResult result, Model model) {

		    try {
		    	if (result.hasErrors()) {
			        model.addAttribute("studentProgramCourse", spc);
			        model.addAttribute("studentProram", spService.selectAllStudentProgram());
			        model.addAttribute("courses", coursesService.selectAllCourses());
			        return "create-studentProgram";
			    }
		    	spcService.createSPC(
		        		spc.getStudentProgram(),
		        		spc.getCourse(),
		        		spc.getMark()
		        );
		        return "redirect:/spc/crud/all";
		    } catch (Exception e) {
		        model.addAttribute("package", e.getMessage());
		        return "show-error";
		    }
		
	}
	//----------------------------------------------------------------
	
	//----------------------------------------------------------------
	
	@GetMapping("/all") //localhost:8081/spc/crud/all
	public String getControllerGetAll(Model model) {
		try {
			List<StudentProgramCourse> allSpc = spcService.selectAllSPC();
			model.addAttribute("studentProgramCourse", allSpc);
			return "spc-all";
		}
		catch(Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
	}
}
