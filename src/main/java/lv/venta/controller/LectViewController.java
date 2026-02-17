package lv.venta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.model.Lecturers;
import lv.venta.model.StudyCourses;
import lv.venta.service.ILectViewService;

@Controller
@RequestMapping("/professor")
public class LectViewController {
	@Autowired
	private ILectViewService lectService;
	
	//-------------AllCourses-------------------
	@GetMapping("/courses") //localhost:8081/professor/courses
	public String getControllerAllProfessorCourses(Model model) {
		try {
		Lecturers foundedLector = lectService.getAuthorisedId();
		List<StudyCourses> allCourses = lectService.allCoursesForLecturer(foundedLector.getLecturersId());
	    System.out.println("Atrasti kursi: " + allCourses.size());
	    model.addAttribute("courses", allCourses);
	    return "courses-all";
	    
		}catch(Exception e){
			model.addAttribute("package", e.getMessage());
			return "show-error";
		}
		
	}
	
}
