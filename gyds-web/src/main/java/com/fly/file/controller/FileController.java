package com.fly.file.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fly.common.service.FileService;
import com.fly.domain.FileDO;
import com.fly.domain.UploadDo;
import com.fly.utils.FileType;
import com.fly.utils.FileUtil;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 文件上传
 */
@Controller
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService sysFileService;

	@Autowired
	private UploadDo bootdoConfig;

	@GetMapping()
	String sysFile(Model model) {
		Map<String, Object> params = new HashMap<>(16);
		return "common/file/file";
	}

	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<FileDO> sysFileList = sysFileService.list(query);
		int total = sysFileService.count(query);
		PageUtils pageUtils = new PageUtils(sysFileList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	String add() {
		return "common/sysFile/add";
	}

	@GetMapping("/edit")
	// @RequiresPermissions("common:bComments")
	String edit(Long id, Model model) {
		FileDO sysFile = sysFileService.get(id);
		model.addAttribute("sysFile", sysFile);
		return "common/sysFile/edit";
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		FileDO sysFile = sysFileService.get(id);
		return R.ok().put("sysFile", sysFile);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save(FileDO sysFile) {
		if (sysFileService.save(sysFile) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody FileDO sysFile) {
		sysFileService.update(sysFile);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	// @RequiresPermissions("common:remove")
	public R remove(Long id, HttpServletRequest request) {
		String fileName = bootdoConfig.getUploadPath() + sysFileService.get(id).getUrl().replace("/files/", "");
		if (sysFileService.remove(id) > 0) {
			boolean b = FileUtil.deleteFile(fileName);
			if (!b) {
				return R.error("数据库记录删除成功，文件删除失败");
			}
			return R.ok();
		} else {
			return R.error();
		}
	}
	/**
	 * 删除
	 */
	/*@PostMapping("/removeByFileName")
	@ResponseBody
	// @RequiresPermissions("common:remove")
	public R remove(String path , HttpServletRequest request) {
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		String fileName = bootdoConfig.getUploadPath() + path.replace("/files/", "");
		if (sysFileService.remove(id) > 0) {
			boolean b = FileUtil.deleteFile(fileName);
			if (!b) {
				return R.error("数据库记录删除成功，文件删除失败");
			}
			return R.ok();
		} else {
			return R.error();
		}
	}*/

	@ResponseBody
	@PostMapping("/upload")
	R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String fileName = file.getOriginalFilename();
		fileName = FileUtil.renameToUUID(fileName);
		FileDO sysFile = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date());
		try {
			FileUtil.uploadFile(file.getBytes(), bootdoConfig.getUpload(), fileName);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}

		if (sysFileService.save(sysFile) > 0) {
			return R.ok().put("fileName",sysFile.getUrl()).put("id",sysFile.getId());
		}
		return R.error();
	}

	@ResponseBody
	@PostMapping("/uploads")
	R uploads(@RequestParam("files") MultipartFile [] files, HttpServletRequest request) {
		List<String> fileNameList=new ArrayList<>();
		if (!(files.length == 0)) {
			// 解析单个文件操作
			for (MultipartFile file : files) {
				String fileName = file.getOriginalFilename();
				fileName = FileUtil.renameToUUID(fileName);
				System.out.println("文件名字***************************"+fileName);
				FileDO sysFile = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date());
				try {
					FileUtil.uploadFile(file.getBytes(), bootdoConfig.getUpload(), fileName);
					fileNameList.add(sysFile.getUrl());
				} catch (Exception e) {
					e.printStackTrace();
					return R.error();
				}
			}
		}else{
			return R.error();
		}
		return R.ok().put("fileNameList",fileNameList);
	}


}
