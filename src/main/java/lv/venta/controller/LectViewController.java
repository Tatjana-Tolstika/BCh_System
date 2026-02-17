package lv.venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.service.ILectViewService;

@Controller
@RequestMapping("/professor")
public class LectViewController {
	@Autowired
	private ILectViewService lectService;
}
