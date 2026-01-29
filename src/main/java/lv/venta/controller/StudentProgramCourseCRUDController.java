package lv.venta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lv.venta.model.StudentProgram;
import lv.venta.model.StudentProgramCourse;
import lv.venta.model.Students;
import lv.venta.model.StudyCourses;
import lv.venta.model.StudyProgram;
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

	        return "create-spc";

	    } catch (Exception e) {
	        model.addAttribute("package", e.getMessage());
	        return "show-error";
	    }
	}
	
	@PostMapping("/add")
	public String postControllerAddNewCourseToStudentProgram(@Valid StudentProgramCourse spc,BindingResult result, Model model) {

		    try {
		    	if (result.hasErrors()) {
			        model.addAttribute("studentProgramCourse", spc);
			        model.addAttribute("studentProrams", spService.selectAllStudentProgram());
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
	//-------------------DELETE-------------------------------------------------------------------------------
	@GetMapping("/delete/{spcId}")//localhost:8081/spc/crud/delete/1
	public String getControllerDeleteStudentProgramCourseById(@PathVariable(name = "spcId") long spcId, Model model)
	{
		try {
			spcService.deleteSPCById(spcId);
			model.addAttribute("package", spcService.retrieveSPCById(spcId));
			return "redirect:/spc/crud/all";
			
		} catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		
	}
	
	//------------------UPDATE----------------------------------------
	@GetMapping("/update/{id}")
	public String getControllerUpdateSPCById(@PathVariable long id, Model model) {
	    try {
	        StudentProgramCourse spcForUpdate = spcService.retrieveSPCById(id);

	        if (spcForUpdate.getCourse() == null) {
	        	spcForUpdate.setCourse(new StudyCourses());
	        }
	        if (spcForUpdate.getStudentProgram() == null) {
	        	spcForUpdate.setStudentProgram(new StudentProgram());
	        }

	        

	        model.addAttribute("studentProgramCourse", spcForUpdate);
	        model.addAttribute("studentPrograms", spService.selectAllStudentProgram());
	        model.addAttribute("courses", coursesService.selectAllCourses());

	        return "update-spc";
	    } catch (Exception e) {
	        model.addAttribute("package", e.getMessage());
	        return "show-error";
	    }
	}
	
	@PostMapping("/update/{id}")
	public String postConstrollerUpdateSPCById(@PathVariable(name = "id") long id, @Valid StudentProgramCourse spc, @RequestParam(name = "studentProgram.studentProgramId") long programId, @RequestParam(name = "course.courseId") long courseId, BindingResult result,Model model) {
		System.out.println(id);
	    System.out.println(spc);
	    
	    try {
	        if (result.hasErrors()) {
	            model.addAttribute("studentProgramCourse", spc); 
	            model.addAttribute("studentPrograms", spService.selectAllStudentProgram());
	            model.addAttribute("courses", coursesService.selectAllCourses());
	            return "update-spc";
	        }
	        
	        
	        spcService.updateStudentProgramCourseById(id, programId, courseId, spc.getMark());
	        return "redirect:/spc/crud/all";
	    }
	    catch(Exception e) {
	        model.addAttribute("package", e.getMessage());
	        return "show-error";
	    }
		
		
		
	}

	//-------------------------------------------------------------------------------------
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
