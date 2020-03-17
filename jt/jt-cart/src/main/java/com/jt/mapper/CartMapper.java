package com.jt.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Delete;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Cart;

public interface CartMapper extends BaseMapper<Cart>{
	List<Cart> findCartListByUserId(Long userId);
	
	void deleteCart(Cart cart);
}
