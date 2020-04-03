package com.tb.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tb.pojo.Item;
import com.tb.pojo.ItemDesc;
import com.tb.util.HttpClientService;
import com.tb.util.ObjectMapperUtil;
@Service
public class ItemServiceImpl implements ItemService{
		
	@Autowired
	private HttpClientService httpClient;
	//查询商品
	@Override
	public Item findtemById(Long id) {
		String url = "http://49.234.86.111:8091/web/items/findItemById";
		Map<String,String> params = new HashMap<String, String>();
		params.put("itemId", ""+id);
		System.out.println(params);
		String doGet = httpClient.doGet(url,params);
		System.out.println("doGet:   "+doGet);
		return ObjectMapperUtil.toObject(doGet, Item.class);
	}
	//查询商品详细信息
	@Override
	public ItemDesc findtemByIdDesc(Long id) {
		String url = "http://49.234.86.111:8091/web/items/findtemByIdDesc";
		Map<String, String> params = new HashMap<String,String>();
		params.put("itemId", ""+id);
		String doGet = httpClient.doGet(url,params);
		return ObjectMapperUtil.toObject(doGet, ItemDesc.class);
	}

}
