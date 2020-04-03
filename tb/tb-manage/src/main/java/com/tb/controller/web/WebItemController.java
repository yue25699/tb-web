package com.tb.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tb.anno.Cache_Find;
import com.tb.pojo.Item;
import com.tb.pojo.ItemDesc;
import com.tb.service.ItemService;

@RestController
@RequestMapping("web/items")
public class WebItemController {
	@Autowired
	private ItemService ItemService;
	//http://localhost:8091/web/item/findItemById?itemId=3213;
	@RequestMapping("findItemById")
	public Item findItemById(Long itemId) {
		Item findItemById = ItemService.findItemById(itemId);
		System.out.println(findItemById);
		return findItemById;
	}
	@RequestMapping("findtemByIdDesc")
	public ItemDesc findtemByIdDesc(Long itemId) {
		return ItemService.findItemDescById(itemId);
	}
	
}
