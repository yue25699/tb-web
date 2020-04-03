package com.tb.service;

import java.util.List;

import com.tb.pojo.ItemCat;
import com.tb.vo.EasyUITree;

public interface ItemCatService {

	ItemCat findItemCatById(Long itemCatId);

	List<EasyUITree> findItemCatByParentId(Long parentId);

	List<EasyUITree> findItemCatByCacheParenatId(Long parentId);


}
