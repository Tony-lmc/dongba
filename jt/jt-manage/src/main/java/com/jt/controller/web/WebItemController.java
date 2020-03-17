package com.jt.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//后台接收用户请求获取商品信息
//manage.jt.com/web/item/findItemById?id=562379

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
@RestController
@RequestMapping("/web/item")
public class WebItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/findItemById")
	public Item findItemById(Long id) {
		Item item = itemService.findItemById(id);
		System.out.println("id:"+id);
		//System.out.println(item.toString());
		return item;
		
	}
	//查询itemDesc对象
	@RequestMapping("/findItemDescById")
	public ItemDesc findItemDescById(Long id) {
		return itemService.findItemDescById(id);
	}

}
