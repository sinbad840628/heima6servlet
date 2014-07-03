package cn.heima6.utils;

import org.junit.Test;

public class ServletUtils {
	/*
	 * --/heima6servlet/HelloWorldAction.action
	 * --HelloWorldAction
	 */
	public static String convert(String s){
		
		String[] ss = s.split("/");
		
		String result = ss[ss.length-1].substring(0, ss[ss.length-1].indexOf("."));
		
		return result;
	}
	@Test
	public void test(){
		System.out.println(ServletUtils.convert("/heima6servlet/HelloWorldAction.action"));
	}
}

