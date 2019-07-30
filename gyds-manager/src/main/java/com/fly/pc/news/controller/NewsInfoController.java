package com.fly.pc.news.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fly.domain.UserDO;
import com.fly.news.dao.CommentDao;
import com.fly.news.dao.PriceDao;
import com.fly.news.dao.RewardInfoDao;
import com.fly.news.dao.TopDao;
import com.fly.news.domain.CommentDO;
import com.fly.news.domain.InfoDO;
import com.fly.news.domain.PriceDO;
import com.fly.news.domain.RewardInfoDO;
import com.fly.news.domain.TopDO;
import com.fly.news.service.InfoService;
import com.fly.system.dao.UserDao;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;
import com.fly.team.domain.TeamDO;
import com.fly.team.service.TeamService;
import com.fly.utils.DateUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

@Controller
@RequestMapping("/pc/news/")
public class NewsInfoController extends BaseDynamicController{
	@Autowired
	private InfoService infoService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private PriceDao priceDao;
	@Autowired
	private RewardInfoDao rewardInfoDao;
	@Autowired
	private UserDao userMapper;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private TopDao topDao;
	@Autowired
	private RegionService regionService;

	@RequestMapping("/info")
	public String newInfo(@RequestParam Integer id,Model model) {
		InfoDO info = infoService.get(id);
		Map<String, Object> params = new HashMap<String, Object>();
		//团队置顶排序
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit",10);
		params.put("teamId", info.getTeamId());
		params.put("sort","n.is_top desc,n.public_time desc");
		List<InfoDO> infoList = infoService.list(params);
		//发布时间排序
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit",10);
		params.put("sort","n.is_top desc,n.public_time desc");
		List<InfoDO> newsLike = infoService.list(params);
		//获取团队信息
		TeamDO teamDO = teamService.get(info.getTeamId());
		//县级置顶3条
		Integer code = upRegCode(info.getTeamId());
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit",3);
		params.put("topRegion", code);
		params.put("sort","n.is_top desc,n.public_time desc");
		List<InfoDO> newsTop = infoService.list(params);
		//市级置顶3条
		code = upRegCode(code);
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit",3);
		params.put("topRegion", code);
		params.put("sort","n.is_top desc,n.public_time desc");
		newsTop.addAll(infoService.list(params));
		//省级置顶3条
		code = upRegCode(code);
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit",3);
		params.put("topRegion", code);
		params.put("sort","n.is_top desc,n.public_time desc");
		newsTop.addAll(infoService.list(params));
		//总平台置顶3条
		code = upRegCode(code);
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit",3);
		params.put("topRegion", code+"");
		params.put("sort","n.is_top desc,n.public_time desc");
		newsTop.addAll(infoService.list(params));
		//是否关注团队
		Integer  a = is_attention(info.getTeamId());
		model.addAttribute("is_attention", a);
		//是否点过赞
		Integer l = is_likes(id);
		//0:未登录 1:点赞 2: 未点赞
		model.addAttribute("isLike", l);
		//是否置过顶
		Integer t = is_top(id);
		//0:未登录 1:置顶 2: 未置顶
		model.addAttribute("isTop", t);
		//打赏人数
		params.clear();
		params.put("newsId",id);
		params.put("sort","create_time desc");
		params.put("group", "member_id");
		List<RewardInfoDO> re = rewardInfoDao.list1(params);
		List<Integer> userIds = new ArrayList<Integer>();
		for(RewardInfoDO user : re) {
			userIds.add(user.getMemberId());
		}
		params.clear();
		params.put("userIds", userIds);
		params.put("offset", 0);
		params.put("limit",15);
		List<UserDO> user = userMapper.list(params);

		//评论 默认时间顺序
		params.clear();
		params.put("newsId", id);
		params.put("sort", "create_time desc");
		List<CommentDO> comm = commentDao.list(params);

		//评论人数
		model.addAttribute("commCount",comm.size());
		//评论默认时间排序
		model.addAttribute("comm",comm);
		//打赏人数
		model.addAttribute("reCountUser",re.size());
		//打赏用户
		model.addAttribute("user",user);
		//置顶排序
		model.addAttribute("newsTop", newsTop);
		//团队信息
		model.addAttribute("team", teamDO);
		//团队咨询列表
		model.addAttribute("newsList", infoList);
		//点赞排行
		model.addAttribute("newsLike",newsLike);
		//新闻详情
		model.addAttribute("info", info);
		return "pc/newInfo";
	}
	//文章分享
	@RequestMapping(value="/share",method=RequestMethod.GET)
	@ResponseBody
	public R shareNewInfoLog(@RequestParam Map<String,Object> para) {
		Integer i = dynamic(para,1);
		R r=new R();
		if(i==1){//不加积分
			r.put("code", 0);
		}else if(i==2) {//积分加1
			r.put("code", 2);
			r.put("msg", "积分+1");
		}else {
			return R.error();
		}
		return r;

	}
	//文章点赞
	@RequestMapping(value="/likes",method=RequestMethod.GET)
	@ResponseBody
	public R likes(@RequestParam Map<String,Object> params) {
		Integer i = dynamic(params,2);
		R r=new R();
		if(i==1){
			r.put("code", 0);
		}else if(i==2) {//积分加1
			r.put("code", 2);
			r.put("msg", "积分+1");
		}else {
			return R.error();
		}
		return r;
	}
	//打赏
	//return 0:扣款失败 -1表示余额不足 1表示扣款成功 2表示无此用户
	@RequestMapping(value="/reward",method=RequestMethod.POST)
	@ResponseBody
	public R reward(@RequestParam Map<String,Object> params) {
 		Integer i = null;
		i = deductMoney(params);
		if(i==1) {
			//产生订单
			if(creadOrder(params)>0){
				//记录+1
				if(dynamic(params,3)==1){
					return R.ok();
				}
			}
		}
		return R.error(i+"");
	}
	//红包
		//return 0:扣款失败 -1表示余额不足 1表示扣款成功 2表示无此用户
		@RequestMapping(value="/redPacket",method=RequestMethod.POST)
		@ResponseBody
		public R redPacket(@RequestParam Map<String,Object> params) {
	 		Integer i = null;
			i = deductMoney(params);
			if(i==1) {
				//产生订单
				if(creadOrder(params)>0){
					//记录+1
					if(dynamic(params,3)==1){
						return R.ok();
					}
				}
			}
			return R.error(i+"");
		}
	//置顶
	@RequestMapping(value="/top/{id}",method=RequestMethod.GET)
	public String top(@PathVariable("id") Integer id,Model model) {
		InfoDO info = infoService.get(id);
		Integer code = info.getTeamId();
		model.addAttribute("teamRegion", code);
		code = upRegCode(code);
		model.addAttribute("areaRegion", code);
		code = upRegCode(code);
		model.addAttribute("cityRegion", code);
		code = upRegCode(code);
		model.addAttribute("proRegion", code);
		code = upRegCode(code);
		model.addAttribute("region", code);
		model.addAttribute("newsId", id);
		return "pc/top";
	}
	//置顶提交
	@ResponseBody
	@PostMapping("/topInfo")
	public R comTopInfo(@RequestParam Map<String,Object> params) {
		TopDO top = new TopDO();
		UserDO user = null; 
		Map<String, BigDecimal> costMap = count(params);
		BigDecimal cost = costMap.get("count");
		params.put("orderType", 2);
		params.put("examineStatus", 2);
		params.put("expIncType", 5);
		params.put("price", cost);
		Integer orderNuber = creadOrder(params);
		if(orderNuber>0) {
			top.setOrdernumber(orderNuber);
			top.setNewsId(Long.parseLong(params.get("newsId").toString()));
			top.setStatus(3);
			top.setTopPrice(cost);
			top.setTopStartTime(DateUtils.parse(params.get("topStartTime").toString()));
			top.setTopEndTime(DateUtils.parse(params.get("topEndTime").toString()));
			top.setRegionCode(Integer.valueOf(params.get("regionCode").toString()));
			user = ShiroUtils.getUser();
			if(user!=null) {
				top.setUserId(Long.parseLong(user.getUserId().toString())); 
			}
			if(topDao.save(top)>0){
				return R.ok();
			}
		}
		return R.error();
	}
	@RequestMapping(value="/count",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, BigDecimal> count(@RequestParam Map<String,Object> params) {
		BigDecimal count = null;
		Integer dayNumber = 0;
		List<PriceDO> pri = priceDao.list(params);
		BigDecimal mon = pri.get(0).getPriceOfDay();
		String StartTime=(String) params.get("topStartTime");
		String EndTime=(String)params.get("topEndTime");
		Date Start = DateUtils.parse(StartTime);
		Date end =DateUtils.parse(EndTime);
		try {
			dayNumber = DateUtils.longOfTwoDate(Start,end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		BigDecimal day = BigDecimal.valueOf(Long.parseLong(dayNumber.toString())) ;
		if(mon!=null&&day!=null) {
			count = mon.multiply(day);
		}
		Map<String,BigDecimal> map = new HashMap<String,BigDecimal>();
		map.put("count", count);
		return map;
	}

	//红包
	@RequestMapping(value="/red",method=RequestMethod.GET)
	public String red(Model model) {

		return "pc/redPacket";
	}

	//评论
	@ResponseBody
	@PostMapping("/comment")
	public R comment(@RequestParam Map<String,Object> params) {
		Integer i =creadComm(params);
		R r=new R();
		if(i == 1) {
			Integer c = dynamic(params,4);
			if(c==1){
				r.put("code", 0);
			}else if(c==2) {
				r.put("code", 2);
				r.put("msg", "积分+1");
			}
		}else if(i==0){
			return R.error("0");
		}
		return r;
	}
	
	@RequestMapping("/infoList")
	public String newInfoList(@RequestParam Integer areaId,@RequestParam Integer flag,Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		// 查询列表数据
			params.put("pids",areaId);
			List<Integer> ids = regionService.getTeamAndAreaByUserRole(params);
			params.put("ids", ids);
			params.put("status", 1);
			params.put("isDel", 0);
			if(flag == 0) {
				params.put("sort","n.is_top desc,n.public_time desc");
			}else if(flag == 1){
				params.put("sort","n.is_red_peper desc,n.public_time desc");
			}else if(flag == 2){
				params.put("sort","n.number_of_likes desc,n.public_time desc");
			}
			//查询列表数据
			List<InfoDO> infoList = infoService.list(params);
			model.addAttribute("newsList", infoList);
			model.addAttribute("areaId", areaId);
		return "pc/newsList";
	}
	
	
		//关注团队
		@ResponseBody
		@PostMapping("/attention")
		public R attention(@RequestParam Map<String,Object> params) {
			Integer i = null;
			// 0:+1失败          1:+1成功            2:表示+1成功&&积分+1       3:获取不到当前用户
			i = dynamic(params,7);
			R r=new R();
			if(i==1) {
				r.put("code", 0);
			}else if(i==3){
				r.put("code", 1);
			}else {
				return R.error("2");
			}
			return r;
		}
}


