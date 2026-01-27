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

import jakarta.validation.Valid;
import lv.venta.model.StudentProgram;
import lv.venta.model.Students;
import lv.venta.model.StudyProgram;
import lv.venta.service.IProgramCRUDService;
import lv.venta.service.IStudentProgramCRUDService;
import lv.venta.service.IStudentsCRUDService;

@Controller
@RequestMapping("/studentProgram/crud")
public class StudentProgramCRUDController {
	@Autowired
	private IStudentProgramCRUDService stService;
	@Autowired
	private IStudentsCRUDService studentService;
	@Autowired
	private IProgramCRUDService programService;
	
	//----------------------ADD---------------------------------------
			@GetMapping("/add") //localhost:8081/studentProgram/crud/add
			public String getControllerAddNewStudentToProgram(Model model) {
				
				try {
					
					List<StudyProgram> allPrograms = programService.selectAllPrograms();
					List<Students> allStudents = studentService.selectAllStudents();
				    System.out.println("Atrastas programmas: " + allPrograms.size());
				    System.out.println("Atrasti studenti: " + allStudents.size());
				    
					model.addAttribute("studentProgram", new StudentProgram());
					model.addAttribute("programs", allPrograms); 
					model.addAttribute("students", allStudents);

			        return "create-studentProgram";

			    } catch (Exception e) {
			        model.addAttribute("package", e.getMessage());
			        return "show-error";
			    }
			}
			
			@PostMapping("/add")
			public String postConstrollerAddNewTestTask(@Valid StudentProgram sp, BindingResult result, Model model) {
		
				    try {
				    	if (result.hasErrors()) {
					        model.addAttribute("studentProgram", sp);
					        model.addAttribute("students", studentService.selectAllStudents());
					        model.addAttribute("programs", programService.selectAllPrograms());
					        return "create-studentProgram";
					    }
				        stService.createStudentProgram(
				            sp.getStudent(),
				            sp.getStudyProgram(),
				            sp.getCourse()
				        );
				        return "redirect:/studentProgram/crud/all";
				    } catch (Exception e) {
				        model.addAttribute("package", e.getMessage());
				        return "show-error";
				    }
				
			}
			//----------------------------------------------------------------
			//-------------------DELETE-------------------------------------------------------------------------------
			@GetMapping("/delete/{programId}/{studentId}")//localhost:8081/studentProgram/crud/delete/1/3
			public String getControllerDeleteTaskById(@PathVariable(name = "programId") long programId, @PathVariable(name = "studentId") long studentId, Model model)
			{
				try {
					stService.deleteStudentFromProgram(studentId, programId);
					model.addAttribute("package", stService.retrieveByStudyProgram(programId));
					return "redirect:/studentProgram/crud/all/" + programId;
					
				} catch (Exception e) {
					model.addAttribute("package", e.getMessage());
					return "show-error";
				}
				
			}
			
			//------------------UPDATE----------------------------------------
			@GetMapping("/update/{spId}") //localhost:8081/testTask/crud/update/1
			public String getControllerUpdateStudentProgram(@PathVariable(name = "spId") long spId, Model model) {
				try {
				StudentProgram spForUpdate = stService.retrieveStudentProgramById(spId);
				model.addAttribute("allStudentPrograms", stService.selectAllStudentProgram());
				model.addAttribute("studentProgram", spForUpdate);
				model.addAttribute("programName", spForUpdate.getStudyProgram());
				return "update-studentProgram";
				}
				catch (Exception e) {
					model.addAttribute("package", e.getMessage());
					return "show-error";
				}
			}
			
			@PostMapping("/update/{spId}")
			public String postControllerUpdateStudentProgram(@PathVariable(name = "spId") long spId, @Valid StudentProgram sp, BindingResult result,Model model) {
				System.out.println(spId);
				System.out.println(sp);
				if (result.hasErrors()) {
					model.addAttribute("studentProgram", sp); 
				model.addAttribute("programs", stService.selectAllStudentProgram());
				  return "update-studentProgram";
				}
				
				try {
					stService.updateStudentProgramById(spId, sp.getStudent().getStudentId(), sp.getStudyProgram().getProgramId(), sp.getCourse());
					return "redirect:/studentProgram/crud/all/" + sp.getStudyProgram().getProgramId();
				}
				catch(Exception e) {
					model.addAttribute("package", e.getMessage());
					return "show-error";
				}
				
			}

			//-------------------------------------------------------------------------------------

			//----------------------------------------------------------------
			
			@GetMapping("/all") //localhost:8081/studentProgram/crud/all
			public String getControllerGetAll(Model model) {
				try {
					List<StudentProgram> allSp = stService.selectAllStudentProgram();
					model.addAttribute("studentProgram", allSp);
					return "studentProgram-all";
				}
				catch(Exception e) {
					model.addAttribute("package", e.getMessage());
					return "show-error";
				}
			}
			
			@GetMapping("/all/{programId}") //localhost:8081/studentProgram/crud/all/1
			public String getControllerGetStudentsByProgram(@PathVariable(name = "programId") long programId, Model model) {
				try {
					List<StudentProgram> foundedStudents = stService.retrieveByStudyProgram(programId);
					model.addAttribute("studentProgram", foundedStudents);
					return "studentProgram-all";
				}
				catch(Exception e) {
					model.addAttribute("package", e.getMessage());
					return "show-error";
				}
			}
}
