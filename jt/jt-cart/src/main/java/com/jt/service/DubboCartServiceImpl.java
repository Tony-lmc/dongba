package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;

@Service(timeout = 3000)
public class DubboCartServiceImpl implements DubboCartService{
	@Autowired
	private CartMapper cartMapper;

	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		QueryWrapper<Cart> queryWrapper = 
				new QueryWrapper<Cart>();
		queryWrapper.eq("user_id", userId);
		return cartMapper.selectList(queryWrapper);

	}

	/**
	 * 修改购物车数量
	 */
	@Transactional
	@Override
	public void updateCartNum(Cart cart) {
		Cart tempCart = new Cart();
		tempCart.setNum(cart.getNum())//更新数量
		.setUpdated(new Date());//更新修改时间
		UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<Cart>();
		updateWrapper.eq("user_id", cart.getUserId())
		.eq("item_id", cart.getItemId());//根据用户名和商品ID修改
		//mybatis更新时 只更新传入了值的字段  这里只更新数量和修改时间
		cartMapper.update(tempCart, updateWrapper);
	}
	/**
	 * 删除购物车商品
	 */
	@Override
	public void deleteCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>(cart);
		cartMapper.delete(queryWrapper);
	}

	/**
	 * 购物车新增
	 * 1.用户第一次新增可以直接入库
	 * 2.用户不是第一次入库.应该只做数量的修改.
	 * 根据:itemId和userId查询数据
	 */
	@Transactional
	@Override
	public void insertCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", cart.getUserId())
		.eq("item_id", cart.getItemId());
		Cart cartDB = cartMapper.selectOne(queryWrapper);
		if(cartDB == null) {
			//用户第一次购买商品 可以直接入库
			cart.setCreated(new Date())
			.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}else {
			//表示用户多次添加购物车 只做数量的修改
			int num = cart.getNum() + cartDB.getNum();
			cartDB.setNum(num)
			.setUpdated(new Date());
			cartMapper.updateById(cartDB);
		}
	}
}
