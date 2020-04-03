package com.tb.service;

import com.tb.pojo.Item;
import com.tb.pojo.ItemDesc;

public interface ItemService {

	Item findtemById(Long id);

	ItemDesc findtemByIdDesc(Long id);

}
