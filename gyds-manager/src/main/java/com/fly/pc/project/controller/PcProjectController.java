package com.fly.pc.project.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.activity.domain.ActivityDO;
import com.fly.activity.service.ActivityService;
import com.fly.domain.UserDO;
import com.fly.helpCenter.domain.TypeTitleDO;
import com.fly.index.service.IndexService;
import com.fly.news.domain.InfoDO;
import com.fly.news.service.InfoService;
import com.fly.pc.utils.PageUtils;
import com.fly.project.domain.ProjectDO;
import com.fly.project.domain.ProjectInfoDO;
import com.fly.project.domain.ProjectTypeDO;
import com.fly.project.service.ProjectInfoService;
import com.fly.project.service.ProjectService;
import com.fly.project.service.ProjectTypeService;
import com.fly.sys.service.SetupService;
import com.fly.system.service.UserService;
import com.fly.system.utils.ShiroUtils;
import com.fly.team.domain.TeamDO;
import com.fly.team.service.TeamService;
import com.fly.utils.Constant;
import com.fly.utils.R;

@Controller
public class PcProjectController {
	@Autowired
	private ProjectInfoService projectInfoService;
	@Autowired
	private ProjectTypeService ProjectTypeService;
	@Autowired
	private IndexService indexService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private SetupService setupService;
	
	@RequestMapping("/pc/proList")
	public String projectList(Long areaId,Map<String, Object> para,Model model) {
		List<ProjectTypeDO> proTypeList = ProjectTypeService.list(para);
		List<ProjectInfoDO> proInfoList = projectInfoService.proInfoList(para);
		List<TypeTitleDO> list2 = indexService.getFooterCenter();
		model.addAttribute("centerList", list2);
		model.addAttribute("proTypeList", proTypeList);
		model.addAttribute("proInfoList", proInfoList);
		model.addAttribute("areaId", areaId);
		model.addAttribute("title",setupService.get(1).getTitle());
		return "pc/liebiao";
	}
	
	@ResponseBody
	@RequestMapping("/pc/queryProByType")
	public List<ProjectInfoDO> queryProByType(Long type){
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("projectType", type);
		List<ProjectInfoDO> proInfoList = projectInfoService.proInfoList(para);
		return proInfoList;
	}
	
	@RequestMapping("/pc/queryProById")
	public String queryProById(Model model,Long id,Long areaId){
		Map<String, Object> map = new HashMap<String, Object>();
		UserDO user = ShiroUtils.getUser();
		if(user!=null){
			map.put("userId", user.getUserId());
			List<TeamDO> userTeam = teamService.list(map);
			if(!userTeam.isEmpty()){
				model.addAttribute("isPro", Constant.Project.CHENG_JIE);
				Long teamId = userTeam.get(0).getId();
				Integer isAuth = userTeam.get(0).getIsAuth();
				if(isAuth!=1) {
					map.put("teamId",teamId);
					map.put("projectId",id);
					map.put("status",2);
					List<ProjectDO> isPro = projectService.list(map);
					if(!isPro.isEmpty()) {
						model.addAttribute("isPro", Constant.Project.YI_CHENG_JIE);
						map.put("teamId",teamId);
						map.put("projectId",id);
						map.put("status",2);
						map.put("isDue",1);
						isPro = projectService.list(map);
						if(!isPro.isEmpty()) {
							model.addAttribute("isPro", Constant.Project.YI_GUO_QI);
						}
					}
				}else {
					model.addAttribute("isPro", Constant.Project.WEI_REN_ZHENG);
				}
			}else {
				model.addAttribute("isPro", Constant.Project.FEI_TUAN_ZHANG);
			}
		}else {
			model.addAttribute("isPro", Constant.Project.WEI_DENG_LU);
		}
		
		map.clear();
		ProjectInfoDO proInfo = projectInfoService.get(id);
		Long teamId=proInfo.getTeamId();
		TeamDO team = teamService.get(teamId);
		map.put("sort", "team_count");
		map.put("order1", "desc");
		List<ProjectInfoDO> rePro = projectInfoService.list(map);
		
		map.clear();
		map.put("projectId",id);
		map.put("status",2);
		List<ProjectDO> teamList= projectService.list(map);
		map.put("status",1);
		List<InfoDO> newsList = infoService.list(map);
		
		map.clear();
		map.put("projectId",id);
		map.put("examineStatus",1);
		List<ActivityDO> actList =  activityService.list(map);
		List<TypeTitleDO> list2 = indexService.getFooterCenter();
		model.addAttribute("centerList", list2);
		model.addAttribute("proInfo",proInfo);
		model.addAttribute("areaId",areaId);
		model.addAttribute("team",team);
		model.addAttribute("rePro",rePro);
		model.addAttribute("teamList",teamList);
		model.addAttribute("newsList",newsList);
		model.addAttribute("actList",actList);
		model.addAttribute("title",setupService.get(1).getTitle());
		return "pc/xiangqing";
	}
	
	@ResponseBody
	@RequestMapping("/pc/savaPro")
	public R savaPro(Long id,Integer order,Integer flag){
		Map<String, Object> map = new HashMap<String, Object>();
		Long userId = ShiroUtils.getUserId();
		map.put("userId", userId);
		List<TeamDO> userTeam = teamService.list(map);
		if(userTeam.isEmpty()) {
			return R.error("抱歉,您不是团长或者您未创建团队");
		}
		Long teamId = userTeam.get(0).getId();
		ProjectDO project = new ProjectDO();
		project.setCreateTime(new Date());
		project.setProjectId(id);
		project.setStatus(1);
		project.setTeamId(teamId);
		project.setOrder(order);
		project.setDuration(flag);
		projectService.save(project);
		return R.ok("已提交,请等待审核,勿重复提交");
	}
	
	@RequestMapping("/pc/queryProList")
	@ResponseBody
	public List<ProjectInfoDO> queryProList(@RequestParam Map<String, Object> para,Integer type, Model model) {
		PageUtils page = new PageUtils(para);
		page.put("status", 1);
		page.put("isDel", 0);
		// 查询列表数据
		List<ProjectInfoDO> proInfoList = projectInfoService.proInfoList(para);
		return proInfoList;
	}
	
	@RequestMapping("/pc/proDue")
	@ResponseBody
	public R proDue(Map<String, Object> para,Long id) {
		ProjectDO projectDO = projectService.get(id);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 366);
		projectDO.setEndTime(c.getTime());
		projectDO.setIsDue(1);
		if(projectService.update(projectDO)>0) {
			return R.ok();
		}
		return R.error();
	}
}
