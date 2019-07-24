package com.fly.pc.persion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PersionController {
	
	
	@RequestMapping("/pc/personalCenter")
	public String getPersionCenter(Model model) {
		model.addAttribute("aaa", 1);
		return "pc/persion_center";
	}
	
	@GetMapping("/pc/persion_test")
	String main() {
		return "pc/persion_test";
	}
}
