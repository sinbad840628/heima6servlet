package cn.heima6.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.heima6.action.Action;
import cn.heima6.utils.ServletUtils;

/**
 * @author Administrator
 *	1、servlet的生命周期
 *		init  doFilter  destroy三个方法描述了filter的生命周期
 *	2、这个filter完成的功能
 *		<a href="helloWorldAction.action">请求一个action</a>
 *		拦截这个请求，把HelloWorldAction提取出来，然后利用java的反射机制进行调用
 *			`前提条件：假设自己写的action都在cn.heima6.action包中
 *			Action action = (Action) Class.forName("cn.heima6.action.HelloWorldAction").newInstance();
 *			action.execute(request,response);
 *		1、获取HelloWorldAction.action中的HelloWorldAction
 *		2、获取HttpServletRequest,HttpServletResponse
 *		3、利用java的反射机制进行调用
 */
public class DispatcherFilter implements Filter{
	
	private HttpServletRequest request;
	private HttpServletResponse response;

	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("destroy");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		/**
		 * 1、获取HttpServletRequest,HttpServletResponse
		 * 2、获取HelloWorldAction.action中的HelloWorldAction
		 * 3、利用java的反射机制进行调用
		 */
		this.request = (HttpServletRequest)request;
		this.response = (HttpServletResponse)response;
		
		String uri = this.request.getRequestURI();
		
		/**
		 *   --/heima6servlet/HelloWorldAction.action
		 * 	 转化成HelloWorldAction
		 */
		String actionName = ServletUtils.convert(uri);
		
		
		//利用java的反射机制调用该方法
		try {
			Action action = (Action)Class.forName("cn.heima6.action."+actionName).newInstance();
			//forward指定要跳转到的jsp页面
			String forward = action.execute(this.request, this.response);

			/*
			 * 跳转的方法
			 * 	* 重定向
			 * 		request里的参数不起作用
			 * 		this.response.sendRedirect(jsp页面);
			 * 	* 转发
			 * 		放在request作用域里的值可以取出来
			 * 		this.request.getRequestDispatcher("index.jsp").forward(this.request, this.response);
			 */
			this.request.getRequestDispatcher("index.jsp").forward(this.request, this.response);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("init");
	}

}
