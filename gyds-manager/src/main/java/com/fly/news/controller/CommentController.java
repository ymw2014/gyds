package com.fly.news.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.news.domain.CommentDO;
import com.fly.news.service.CommentService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;


/**
 * 咨询评论表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-09 17:41:16
 */
 
@Controller
@RequestMapping("/news/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;
	private Integer id ;
	@GetMapping()
	@RequiresPermissions("news:comment:comment")
	String Comment(){
	    return "news/comment/comment";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("news:comment:comment")
	public PageUtils list(@RequestParam Map<String, Object> params){
		params.put("newsId", id);
		//查询列表数据
        Query query = new Query(params);
		List<CommentDO> commentList = commentService.list(query);
		int total = commentService.count(query);
		PageUtils pageUtils = new PageUtils(commentList, total);
		params.put("newsId", "");
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("news:comment:add")
	String add(){
	    return "news/comment/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("news:comment:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		CommentDO comment = commentService.get(id);
		model.addAttribute("comment", comment);
	    return "news/comment/edit";
	}
	
	@GetMapping("/comList/{id}")
	@RequiresPermissions("news:comment:comList")
	String audit(@PathVariable("id") Integer id){
		this.id=id;
	    return "news/comment/comment";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("news:comment:add")
	public R save( CommentDO comment){
		if(commentService.save(comment)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("news:comment:edit")
	public R update( CommentDO comment){
		commentService.update(comment);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("news:comment:remove")
	public R remove( Integer id){
		if(commentService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("news:comment:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		commentService.batchRemove(ids);
		return R.ok();
	}
	
}
