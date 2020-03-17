package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIData;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public EasyUIData findItemByPage(Integer page, Integer rows) {
		//获取商品总记录数
		int total = itemMapper.selectCount(null);
		
		//分页查询
		int start = (page-1)*rows;
		List<Item> itemList = itemMapper.findItemByPage(start,rows);
		return new EasyUIData(total,itemList);
	}
	
	/**
	 * 新增商品
	 */
	@Transactional//事务控制
	@Override
	public void saveItem(Item item,ItemDesc itemDesc) {
		item.setStatus(1)
			.setCreated(new Date())
			.setUpdated(item.getCreated());
		itemMapper.insert(item);
		//新增商品描述信息
		itemDesc.setItemId(item.getId())
				.setCreated(item.getCreated())
				.setUpdated(item.getUpdated());
		itemDescMapper.insert(itemDesc);
	}
	/**
	 * 修改商品
	 */
	@Transactional
	@Override
	public void updateItem(Item item,ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		//同时修改2张表数据
		itemDesc.setItemId(item.getId())
				.setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);

	}
	
	/**
	 * 删除选中商品
	 */
	@Transactional
	@Override
	public void deleteItem(Long[] ids) {
		//1.手动删除
		//itemMapper.deleteItem(ids);
		//2.利用Mybatis-plus自动删除
		List<Long> itemList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(itemList);
		//2张表一起删除
		itemDescMapper.deleteBatchIds(itemList);
	}

	/**
	 * 商品的下架与上架
	 */
	@Override
	public void updateStatus(Long[] ids, int status) {
		Item item = new Item();
		item.setStatus(status).setUpdated(new Date());
		List<Long> longIds = Arrays.asList(ids);//数组转化为集合
		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<Item>();
		updateWrapper.in("id", ids);
		itemMapper.update(item, updateWrapper);
	}
	
	//商品描述信息的回显
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		return itemDescMapper.selectById(itemId);
	}
	
	//前台查询后台商品信息
	@Override
	public Item findItemById(Long id) {
		return itemMapper.selectById(id);//调用mybatisplus查询商品信息
	}
	
}
