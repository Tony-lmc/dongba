package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	/**
	 * restFul:
	 * 		1.参数必须使用/分割
	 * 		2.参数位置必须固定
	 * 		3.接受参数必须使用特定注解，且名字最好一致
	 * @param moduleName
	 * @return
	 */
	
	@RequestMapping("/page/{moduleName}")
	public String module(@PathVariable String moduleName) {//@PathVariable 把参数值赋值给了moduleName
		
		return moduleName;
	}
}
