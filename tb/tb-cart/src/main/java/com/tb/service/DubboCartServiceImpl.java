package com.tb.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tb.mapper.CartMapper;
import com.tb.pojo.Cart;
@Service(timeout = 3000)
public class DubboCartServiceImpl implements DubboCartService{
	
	@Autowired
	private CartMapper cartMapper;
	
	@Override
	public List<Cart> findCartListByUserId(Long id) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper();
		queryWrapper.eq("user_id",id);
		return 	cartMapper.selectList(queryWrapper);
	}

	@Override
	public void updateNum(Cart cart) {
		System.out.println("进入修改界面");
		Cart cart2 = new Cart();
		cart2.setNum(cart.getNum())
		.setUpdated(new Date());
		UpdateWrapper<Cart> updateWrapper = new UpdateWrapper();
		updateWrapper.eq("user_id",cart.getUserId()) .eq("item_id",cart.getItemId());
		cartMapper.update(cart2,updateWrapper);
	}

	//删除命令  delete from tb_cart where ------
	@Override
	public void delectCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper(cart);
		cartMapper.delete(queryWrapper);
	}

	@Override
	@Transactional
	public void insertCart(Cart cart) {
		//入库操作之前查询数据库
		QueryWrapper<Cart> queryWrapper = new QueryWrapper();
		queryWrapper.eq("user_id",cart.getUserId());
		queryWrapper.eq("item_id",cart.getItemId());
		 Cart selectOne = cartMapper.selectOne(queryWrapper);
		 if(selectOne != null) {
			 Cart cart2 = new Cart();
			 cart2.setNum(selectOne.getNum()+cart.getNum()).setUpdated(new Date());
			 cartMapper.update(cart, queryWrapper);
		 }else {
			 cart.setUpdated(new Date());
			 cart.setCreated(new Date());
			 cartMapper.insert(cart);
		 }
	}
}
