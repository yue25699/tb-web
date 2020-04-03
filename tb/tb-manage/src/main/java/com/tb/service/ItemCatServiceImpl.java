package com.tb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tb.mapper.ItemCatMapper;
import com.tb.pojo.ItemCat;
import com.tb.util.ObjectMapperUtil;
import com.tb.vo.EasyUITree;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private ItemCatMapper itemCatMapper;

	@Override
	public ItemCat findItemCatById(Long itemCatId) {
		
		return itemCatMapper.selectById(itemCatId);
	}
//	@Autowired
//	private Jedis redis;
	@Autowired
	private ShardedJedis jedis;
	
	/**
	 * 1.根据parentId查询数据库记录
	 * 2.循环遍历数据,之后封装EasyUITree的list集合
	 */
	@Override
	public List<EasyUITree> findItemCatByParentId(Long parentId) {
		//1.查询数据
		List<ItemCat> itemCatList = 
					  findItemCatListByParentId(parentId);
		//2.实现数据封装
		List<EasyUITree> treeList = 
				new ArrayList<EasyUITree>(itemCatList.size());
		for (ItemCat itemCat : itemCatList) {
			Long id = itemCat.getId();
			String text = itemCat.getName();
			//如果是父级 closed,否则 表示3级标题 open
			String state = itemCat.getIsParent()?"closed":"open";
			EasyUITree tree = new EasyUITree(id, text, state);
			treeList.add(tree);
		}
		
		return treeList;
	}

	private List<ItemCat> findItemCatListByParentId(Long parentId) {
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
		List<ItemCat> itemCatList = 
				itemCatMapper.selectList(queryWrapper);
		return itemCatList;
	}

	@Override
	public List<EasyUITree> findItemCatByCacheParenatId(Long parentId) {
		long time1 = System.currentTimeMillis();
		String key ="ITEM_CAT_"+parentId;
		System.out.println("redis:+"+jedis);
		String string = jedis.get(key);
		List<EasyUITree> treeList =new ArrayList<EasyUITree>();
		if(StringUtils.isEmpty(string)) {
			treeList = findItemCatByParentId(parentId);
			String json = ObjectMapperUtil.toJSON(treeList);
			jedis.setex(key, 7*24*3600, json);
		}else {
			treeList=ObjectMapperUtil.toObject(string, treeList.getClass());
			System.out.println(treeList);
		}
		long time2 = System.currentTimeMillis();
		System.out.println(time2-time1);
		return treeList;
	}
	
	
	
	
	
	
	
	
	
}
