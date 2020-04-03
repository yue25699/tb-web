package com.tb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tb.pojo.Item;
import com.tb.pojo.ItemDesc;
import com.tb.service.ItemService;

@CrossOrigin
@Controller
@RequestMapping("/items")
public class ItemController {
	@Autowired
	private ItemService itemService;
	@RequestMapping("{id}")
	public String items(@PathVariable Long id,Model model) {
		System.out.println(id);
		Item item=itemService.findtemById(id);
		ItemDesc itemDesc=itemService.findtemByIdDesc(id);
		model.addAttribute("item",item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}
}
