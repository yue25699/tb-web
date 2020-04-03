package com.tb.service;

import com.tb.pojo.Item;
import com.tb.pojo.ItemDesc;
import com.tb.vo.EasyUITable;

public interface ItemService {

	EasyUITable findItemByPage(Integer page, Integer rows);

	void saveItem(Item item, ItemDesc itemDesc);

	void updateItem(Item item, ItemDesc itemDesc);

	void updateStatus(Long[] ids, Integer status);

	void deleteItem(Long[] ids);

	ItemDesc findItemDescById(Long itemId);

	Item findItemById(Long itemId);
	
}
