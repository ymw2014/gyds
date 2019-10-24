package com.fly.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fly.ueditor.ActionEnter;


@RestController
@RequestMapping("ueditor")
public class UEditorController {
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/upload")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        logger.debug("******************富文本编辑器初始化************************************");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
	
	@RequestMapping(value="/imgUpload")
    public void uploadImage() {
		
    }

}
