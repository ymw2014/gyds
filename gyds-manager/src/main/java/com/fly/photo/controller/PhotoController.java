package com.fly.photo.controller;

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

import com.fly.photo.domain.PhotoDO;
import com.fly.photo.service.PhotoService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 相册表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-24 14:00:11
 */
 
@Controller
@RequestMapping("/photo/photo")
public class PhotoController {
	@Autowired
	private PhotoService photoService;
	
	@GetMapping()
	@RequiresPermissions("photo:photo:photo")
	String Photo(){
	    return "photo/photo/photo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("photo:photo:photo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<PhotoDO> photoList = photoService.list(query);
		int total = photoService.count(query);
		PageUtils pageUtils = new PageUtils(photoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("photo:photo:add")
	String add(){
	    return "photo/photo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("photo:photo:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		PhotoDO photo = photoService.get(id);
		model.addAttribute("photo", photo);
	    return "photo/photo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("photo:photo:add")
	public R save( PhotoDO photo){
		if(photoService.save(photo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("photo:photo:edit")
	public R update( PhotoDO photo){
		photoService.update(photo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("photo:photo:remove")
	public R remove( Integer id){
		if(photoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("photo:photo:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		photoService.batchRemove(ids);
		return R.ok();
	}
	
}
