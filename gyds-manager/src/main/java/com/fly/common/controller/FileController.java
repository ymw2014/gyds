package com.fly.common.controller;


import com.fly.AliyunOSS.AliyunOSSUtil;
import com.fly.AliyunOSS.ConstantProperties;
import com.fly.AliyunOSS.DeleteFileUtil;
import com.fly.common.controller.BaseController;
import com.fly.common.service.FileService;
import com.fly.domain.FileDO;
import com.fly.domain.UploadDo;
import com.fly.utils.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传
 */
@Controller
@RequestMapping("/common/sysFile")
public class FileController extends BaseController {
	
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FileService sysFileService;

	@Autowired
	private UploadDo bootdoConfig;
	@Value("${java4all.file.bucketname1}")
    private String java4all_file_bucketname1;
	

	@GetMapping()
	@RequiresPermissions("common:sysFile:sysFile")
	String sysFile(Model model) {
		Map<String, Object> params = new HashMap<>(16);
		return "common/file/file";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("common:sysFile:sysFile")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<FileDO> sysFileList = sysFileService.list(query);
		int total = sysFileService.count(query);
		PageUtils pageUtils = new PageUtils(sysFileList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	// @RequiresPermissions("common:bComments")
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
	@RequiresPermissions("common:info")
	public R info(@PathVariable("id") Long id) {
		FileDO sysFile = sysFileService.get(id);
		return R.ok().put("sysFile", sysFile);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("common:save")
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
	@RequiresPermissions("common:update")
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
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
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

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("common:remove")
	public R remove(@RequestParam("ids[]") Long[] ids) {
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		sysFileService.batchRemove(ids);
		return R.ok();
	}

	@ResponseBody
	@PostMapping("/upload")
	R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		/*if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		String fileName = file.getOriginalFilename();
		fileName = FileUtil.renameToUUID(fileName);
		FileDO sysFile = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date());
		try {
			FileUtil.uploadFile(file.getBytes(), bootdoConfig.getUploadPath(), fileName);
		} catch (Exception e) {
			return R.error();
		}

		if (sysFileService.save(sysFile) > 0) {
			return R.ok().put("fileName",sysFile.getUrl()).put("id",sysFile.getId());
		}
		return R.error();*/
		
		 logger.info("============>文件上传");
	        try {
	            if(null != file){
	                String filename = file.getOriginalFilename();
	                if(!"".equals(filename.trim())){
	                    File newFile = new File(filename);
	                    FileOutputStream os = new FileOutputStream(newFile);
	                    os.write(file.getBytes());
	                    os.close();
	                    os.flush();
//	                    file.transferTo(newFile);
	                    //上传到OSS
	                    String uploadUrl = AliyunOSSUtil.upload(newFile);

	                    //上传图片的时候图片会保留在本地项目
	                    File file1 = new File("");
	                    String s = file1.getAbsolutePath();
	                    DeleteFileUtil.delete(s + "\\" + filename);
	                    logger.info("上传图片路径"+uploadUrl);
	                    //return "上传成功";
	                    return R.ok().put("fileName", ConstantProperties.JAVA4ALL_FILE_URL+"/"+uploadUrl).put("id", UUID.randomUUID().toString());
	                }
	            }
	        }catch (Exception ex){
	            ex.printStackTrace();
	        }
			return R.error();
	        
	}

	@ResponseBody
	@PostMapping("/uploads")
	R uploads(@RequestParam("files") MultipartFile[] file, HttpServletRequest request) {
		List<FileDO> list = new ArrayList<FileDO>();
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if(file!=null && file.length>0) {
			for (int i = 0; i < file.length; i++) {
				String fileName = file[i].getOriginalFilename();
				fileName = FileUtil.renameToUUID(fileName);
				FileDO sysFile = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date());
				try {
					FileUtil.uploadFile(file[i].getBytes(), bootdoConfig.getUploadPath(), fileName);
				} catch (Exception e) {
					return R.error();
				}
				if (sysFileService.save(sysFile) > 0) {
					list.add(sysFile);
				}
			}
			return R.ok().put("fileNameList",list);
		}

		return R.error();
	}

}
