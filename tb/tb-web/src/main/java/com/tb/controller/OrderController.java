package com.tb.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tb.pojo.Cart;
import com.tb.service.DubboCartService;
import com.tb.service.DubboOrderService;
import com.tb.util.UserThreadLocal;
import com.tb.vo.SysResult;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Reference(timeout = 3000)
	private DubboOrderService orderService;
	
	@Reference(timeout = 3000)
	private DubboCartService cartService;
	
	@RequestMapping("/create")
	public String create(Model model) {
		Long id = UserThreadLocal.get().getId();
		List<Cart> carts = cartService.findCartListByUserId(id);
		model.addAttribute("cart", carts);
		return "order-cart";
	}

}
