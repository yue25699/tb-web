package com.tb.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tb.pojo.Cart;
import com.tb.service.DubboCartService;
import com.tb.util.UserThreadLocal;
import com.tb.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Reference(timeout = 3000)
	private DubboCartService cartService;
	
	
	//购物车系统
	
	@RequestMapping("/delete/{itemId}")
	public String deleteShow(Cart cart) {
//		Long userId=UserThreadLocal.get().getId();
		Long userId=7L;
		System.out.println("用户id ： "+userId);
		cart.setUserId(userId);
		cartService.delectCart(cart);
		return "redirect:/cart/show.html";
	}
	@RequestMapping("/add/{itemId}")
	public String saveCart(Cart cart) {
//		Long userId=UserThreadLocal.get().getId();
		Long userId=7L;
		System.out.println("用户id ： "+userId);
		cart.setUserId(userId);
		cartService.insertCart(cart);
		return "redirect:/cart/show.html";
		
	}
	
	@RequestMapping("/show")
	public String show(Model model) {
//		Long userId=UserThreadLocal.get().getId();
		Long userId=7L;
		System.out.println("用户id ： "+userId);
		List<Cart> cartList = cartService.findCartListByUserId(userId);
		model.addAttribute("cartList",cartList);
		return "cart";
	}
	
	//2.修改购物车的数量
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateNum(Cart cart) {
//		Long userId=UserThreadLocal.get().getId();
		Long userId=7L;
		System.out.println("用户id ： "+userId);
		cart.setUserId(userId);
		cartService.updateNum(cart);
		
		return SysResult.success();
		
	}
}

