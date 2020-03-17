package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;

@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;
	/**
	 * 1.获取用户文件信息（文件名称）
	 * 2.指定文件上传路径
	 * 3.实现文件上传
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/file")
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException {//MultipartFile springmvc封装的用于操作
		//获取input标签中name属性
		String inputName = fileImage.getName();
		System.out.println("fileimage:"+fileImage);
		System.out.println("inputimage:"+inputName);
		//获取文件名称
		String fileName = fileImage.getOriginalFilename();
		System.out.println("filename:"+fileName);
		//定义文件夹路径
		File fileDir = new File("D:/jt/image");
		if(!fileDir.exists()) {
			fileDir.mkdirs();//如果文件不存在则创建文件，mkdirs可以创建多级目录
		}
		//实现文件上传
		fileImage.transferTo(new File("D:/jt/image/"+fileName));
		return "redirect:/file.jsp";//实现重定向返回文件上传页面
	}
	
	/**
	 * 实现图片的上传
	 * @param uploadFile
	 * @return
	 */
	@RequestMapping("/pic/upload")
	@ResponseBody
	public ImageVO uploadFile(MultipartFile uploadFile) {
		return fileService.updateFile(uploadFile);
	}
}
