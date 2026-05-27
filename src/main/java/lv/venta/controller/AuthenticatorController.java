package lv.venta.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticatorController {

	
	@GetMapping("/home")
	public String home(Authentication auth) {

	    String role = auth.getAuthorities().iterator().next().getAuthority();

	    if("ADMIN".equals(role)) {
	        return "redirect:/admin/courses/crud/all";
	    }

	    if("LECT".equals(role)) {
	        return "redirect:/professor/courses";
	    }

	    if("STUDENT".equals(role)) {
	        return "redirect:/student/courses";
	    }

	    return "show-error";
	}
}
