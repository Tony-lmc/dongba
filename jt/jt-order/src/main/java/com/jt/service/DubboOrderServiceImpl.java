package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

@Service
public class DubboOrderServiceImpl implements DubboOrderService{
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderMapper orderMapper;
	
	/**
	 * 订单入库
	 */
	@Transactional
	@Override
	public String insertOrder(Order order) {
		//order不为自增型，而是人为构造。 ""放前面，先讲前两个相加变为字符串
		String orderId = ""+order.getUserId()+System.currentTimeMillis();
		Date date = new Date();
		
		//状态：1、未付款2、已付款3、未发货4、已发货5、交易成功6、交易关闭
		//入库订单
		order.setOrderId(orderId)
			 .setStatus(1)
			 .setCreated(date)
			 .setUpdated(date);
		orderMapper.insert(order);
		System.out.println("订单入库成功！");
		
		//入库物流
		OrderShipping shipping = order.getOrderShipping();
		shipping.setOrderId(orderId)
		        .setCreated(date)
		        .setUpdated(date);
		orderShippingMapper.insert(shipping);
		System.out.println("订单物流入库成功!!!!!");
		
		//4.入库订单商品  1自己遍历集合分别入库  2.自己编辑sql语句批量入库
		//insert into order_items(xxxx,xxx,xxx) values(xxxx,xxx....),(xxx,xxx,xxx),(xxxxx)
		List<OrderItem> orderLists = order.getOrderItems();
		for (OrderItem orderItem : orderLists) {
			orderItem.setOrderId(orderId)
					 .setCreated(date)
					 .setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		System.out.println("订单商品入库成功!!!");

		return orderId;
	}
	
	/**
	 * 订单信息回显
	 */
	@Override
	public Order findOrderById(String id) {
		Order order = orderMapper.selectById(id);
		OrderShipping shipping = orderShippingMapper.selectById(id);
		QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<OrderItem>();
		queryWrapper.eq("order_id",id);
		List<OrderItem> list = orderItemMapper.selectList(queryWrapper);
		order.setOrderItems(list)
			 .setOrderShipping(shipping);
		return order;
	}

	
	
}
