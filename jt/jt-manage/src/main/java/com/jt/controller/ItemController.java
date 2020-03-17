package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUIData;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/query")
	public EasyUIData findItemByPage(Integer page,Integer rows) {
		EasyUIData findItemByPage = itemService.findItemByPage(page,rows);//返回easyuidata给EasyUI界面使用，需要total和rows
		return findItemByPage;
	}

	/**
	 * 新增商品
	 * @return
	 */
	@RequestMapping("/save")
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
		try {
			itemService.saveItem(item,itemDesc);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}

	//修改商品 tb_item和tb_item_desc是一对一关联关系.所以需要同时维护.
	@RequestMapping("/update")
	public SysResult updateItem(Item item, ItemDesc itemDesc) {
		try {
			itemService.updateItem(item,itemDesc);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	//删除商品
	@RequestMapping("/delete")
	public SysResult deleteItem(Long[] ids) {
		try {
			itemService.deleteItem(ids);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	//商品下架
	@RequestMapping("/instock")
	public SysResult instock(Long[] ids) {
		try {
			int status = 2;//状态码修改为2 商品下架
			itemService.updateStatus(ids,status);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	//商品上架
	@RequestMapping("/reshelf")
	public SysResult reshelf(Long[] ids) {
		try {
			int status = 1;//状态码修改为2 商品下架
			itemService.updateStatus(ids,status);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//restful风格  商品详情的回显
	@RequestMapping("/query/item/desc/{itemId}")
	public SysResult findItemDescById(@PathVariable Long itemId) {
		try {
			ItemDesc itemDesc = itemService.findItemDescById(itemId);
			return SysResult.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}


}
