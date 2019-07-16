package com.fly.pc.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fly.news.domain.InfoDO;
import com.fly.news.service.InfoService;

@Controller
@RequestMapping("/news/")
public class NewsInfoController {
	@Autowired
	private InfoService infoService;
	
	
	
	@RequestMapping("/info/{id}")
	public String newInfo(@PathVariable("id") Integer id,Model model) {
		InfoDO info = infoService.get(id);
		model.addAttribute("info", info);
		return "/pc/newInfo";
	}

}
