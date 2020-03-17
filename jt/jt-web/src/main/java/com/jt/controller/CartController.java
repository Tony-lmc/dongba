package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Reference(timeout=3000,check=false)
	private DubboCartService cartService;
	/**
	 * 1.实现商品列表信息展现
	 * 2.页面取值: ${cartList}
	 */
	@RequestMapping("/show")
	public String findCartList(Model model) {
		//User user = (User) request.getAttribute("JT_USER");
		//Long userId = user.getId(); 
		Long userId = UserThreadLocal.get().getId();;
		List<Cart> cartList = cartService.findCartListByUserId(userId);//通过用户id查询购物车信息
		model.addAttribute("cartList", cartList);
		return "cart";//返回页面逻辑名称
	}
	
	
	/**
	 * 实现购物车数量的修改
	 * url地址:http://www.jt.com/cart/update/num/562379/8
	 * @return
	 */
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(Cart cart) {
		try {
			//Long userId = 7L;
			//User user = (User) request.getAttribute("JT_USER");
			//Long userId = user.getId();
			
			Long userId = UserThreadLocal.get().getId();
			cart.setUserId(userId);
			cartService.updateCartNum(cart);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	/**
	 * 删除购物车商品
	 */
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(Cart cart) {
		Long userId = UserThreadLocal.get().getId();;
		cart.setUserId(userId);
		cartService.deleteCart(cart);
		return "redirect:/cart/show.html";
	}
	/**
	 * url: http://www.jt.com/cart/add/1474391972.html
	 * 
	 * 
	 */
	@RequestMapping("/add/{itemId}")
	public String insertCart(Cart cart) {
		Long userId = UserThreadLocal.get().getId();;
		cart.setUserId(userId);
		cartService.insertCart(cart);
		return "redirect:/cart/show.html";//重定向 新增数据更新
	}
}

