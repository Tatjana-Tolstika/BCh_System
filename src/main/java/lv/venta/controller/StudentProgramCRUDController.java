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
import lv.venta.model.Students;
import lv.venta.model.StudyProgram;
import lv.venta.service.IProgramCRUDService;
import lv.venta.service.IStudentProgramCRUDService;
import lv.venta.service.IStudentsCRUDService;

@Controller
@RequestMapping("/admin/studentProgram/crud")
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
					model.addAttribute("package", stService.selectAllStudentProgram());
					return "redirect:/studentProgram/crud/all/" + programId;
					
				} catch (Exception e) {
					model.addAttribute("package", e.getMessage());
					return "show-error";
				}
				
			}
			
			//------------------UPDATE----------------------------------------
			@GetMapping("/update/{id}")//localhost:8081/studentProgram/crud/update/3
			public String getControllerUpdateStudentProgramById(@PathVariable(name = "id") long id, Model model) {
			    try {
			        StudentProgram spForUpdate = stService.retrieveStudentProgramById(id);
			        System.out.println("Esošā programma: " + spForUpdate.getStudyProgram().getProgramTitle());
			        
			        Students currentStudent = spForUpdate.getStudent();
			        
			        if (spForUpdate.getStudyProgram() == null) {
			            spForUpdate.setStudyProgram(new StudyProgram());
			        }
			        model.addAttribute("programLength", spForUpdate.getStudyProgram().getLength());
			        model.addAttribute("programs", programService.selectAllPrograms());
			        model.addAttribute("studentProgram", spForUpdate);
			        model.addAttribute("student", currentStudent);
			        return "update-studentProgram";
			    } catch (Exception e) {
			        model.addAttribute("package", e.getMessage());
			        return "show-error";
			    }
			}
			
			@PostMapping("/update/{id}")
			public String postConstrollerUpdateStudentProgramById(@PathVariable(name = "id") long id, @Valid StudentProgram sp, BindingResult result,Model model,  @RequestParam(name = "studyProgram.programId") long programId) {
				//RequestParam helps to take an ID from HTML file to avoid errors with nullPointer
				System.out.println(id);
			    System.out.println(sp);
			    
			    try {
			        
			        if (result.hasErrors()) {
			            model.addAttribute("studentProgram", sp); 
			            model.addAttribute("programs", programService.selectAllPrograms());
			            model.addAttribute("student", sp.getStudent());
			            model.addAttribute("fixedId", id);
			            return "update-studentProgram";
			        }
			        
			        //long studentId = currentSp.getStudent().getStudentId(); // Use existing student ID
			        
			        stService.updateStudentProgramById(id, sp.getStudent().getStudentId(), programId, sp.getCourse());
			        return "redirect:/studentProgram/crud/all";
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
