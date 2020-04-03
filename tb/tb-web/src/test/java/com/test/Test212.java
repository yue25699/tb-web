package com.test;

import org.junit.Test;

public class Test212 {

	@Test
	public void Test01() {
		byte a =(byte) (127+1);
		System.out.println( a);
	}
	
	@Test 
	public void Test02() {
		Integer key =6;
		switch (key) {
		case 1:System.out.println("打印1"); 
		case 2:System.out.println("打印2"); 
		case 3:System.out.println("打印3"); 
		case 4:System.out.println("打印4"); 
		case 5:System.out.println("打印5"); 
			default :{
				System.out.println("打印6");
			};
			}
	}
}
