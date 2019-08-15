package com.fly.ueditor.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fly.ueditor.PathFormat;
import com.fly.ueditor.define.AppInfo;
import com.fly.ueditor.define.BaseState;
import com.fly.ueditor.define.FileType;
import com.fly.ueditor.define.State;
import com.fly.utils.AliyunOSSUtil;
import com.fly.utils.ConstantProperties;
import com.fly.utils.DeleteFileUtil;



public class BinaryUploader {

	public static final State save(HttpServletRequest request,
			Map<String, Object> conf) {
		FileItemStream fileStream = null;
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }

		try {
			FileItemIterator iterator = upload.getItemIterator(request);

			while (iterator.hasNext()) {
				fileStream = iterator.next();

				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile(conf.get("fieldName").toString());
			if(multipartFile==null){
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}


			String savePath = (String) conf.get("savePath");
			String originFileName = multipartFile.getOriginalFilename();
			String suffix = FileType.getSuffixByFilename(originFileName);
			String uuid = UUID.randomUUID().toString(); 
			uuid = uuid.replace("-", ""); 
			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());
			savePath = savePath +uuid+ suffix;

			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			savePath = PathFormat.parse(savePath,originFileName);

			String physicalPath = (String)conf.get("basePath") + savePath;

			String filename = multipartFile.getOriginalFilename();
			String uploadUrl="";
            if(!"".equals(filename.trim())){
                File newFile = new File(uuid+suffix);
                FileOutputStream os = new FileOutputStream(newFile);
                os.write(multipartFile.getBytes());
                os.close();
                os.flush();
//                file.transferTo(newFile);
                //上传到OSS
                uploadUrl = AliyunOSSUtil.upload(newFile);

                //上传图片的时候图片会保留在本地项目
                File file1 = new File("");
                String s = file1.getAbsolutePath();
                DeleteFileUtil.delete(s + "\\" + filename);
                //return "上传成功";
            }
			BaseState storageState=new BaseState();
			if (storageState.isSuccess()) {
				storageState.putInfo("url", ConstantProperties.JAVA4ALL_FILE_URL+"/"+uploadUrl);
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
			}

			return storageState;
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
