package com.fly.wxpay.controller;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.index.utils.OrderType;
import com.fly.order.service.OrderService;
import com.fly.system.service.UserService;
import com.fly.utils.R;
import com.fly.wxpay.service.PayService;

@Controller
@RequestMapping("/cashout/")
public class CashoutController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private PayService PayOrderService;
	
	
	@RequestMapping("/createOrder")
	@ResponseBody
	public R createOrder(String fee) {
		if(Integer.valueOf(fee)<10) {
			R r = new R();
			r.put("code", -2);
			return r;
		}
		R r = PayOrderService.createOrder(fee, OrderType.TI_XIAN);
		return r;
	}
	
	
	
	
	@RequestMapping("/queryOrder")
	@ResponseBody
	public R queryOrder(String orderNum) {
		return PayOrderService.queryOrder(orderNum);
	}
	
	/**
	 * 获取ip地址
	 * @return
	 */
	private String localIp() {
		String ip = null;
		Enumeration allNetInterfaces;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
				for (InterfaceAddress add : InterfaceAddress) {
					InetAddress Ip = add.getAddress();
					if (Ip != null && Ip instanceof Inet4Address) {
						ip = Ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			logger.warn("获取本机Ip失败:异常信息:" + e.getMessage());
		}
		return ip;
	}
}
