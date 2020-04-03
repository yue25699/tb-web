package com.tb.service;

import java.util.List;

import com.tb.pojo.Cart;

public interface DubboCartService {

	List<Cart> findCartListByUserId(Long id);

	void updateNum(Cart cart);

	void delectCart(Cart cart);

	void insertCart(Cart cart);


}
