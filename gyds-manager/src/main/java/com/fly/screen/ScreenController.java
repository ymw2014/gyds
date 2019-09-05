package com.fly.screen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.screen.dao.ScreenDao;
@Controller
@RequestMapping("/pc/screen")
public class ScreenController {
	@Autowired
	private ScreenDao screen;
	
	@RequestMapping("/queryScreen")
	public String OrgSurvey(){
		return "/pc/screen" ;
	}
	
	@RequestMapping("/OrgSurvey")
	@ResponseBody
	public List<Map<String, Object>> queryScreen(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> orgSurvey = screen.getOrgSurvey(map);
		return orgSurvey ;
	}
}
