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
 *	1��servlet����������
 *		init  doFilter  destroy��������������filter����������
 *	2�����filter��ɵĹ���
 *		<a href="helloWorldAction.action">����һ��action</a>
 *		����������󣬰�HelloWorldAction��ȡ������Ȼ������java�ķ�����ƽ��е���
 *			`ǰ�������������Լ�д��action����cn.heima6.action����
 *			Action action = (Action) Class.forName("cn.heima6.action.HelloWorldAction").newInstance();
 *			action.execute(request,response);
 *		1����ȡHelloWorldAction.action�е�HelloWorldAction
 *		2����ȡHttpServletRequest,HttpServletResponse
 *		3������java�ķ�����ƽ��е���
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
		 * 1����ȡHttpServletRequest,HttpServletResponse
		 * 2����ȡHelloWorldAction.action�е�HelloWorldAction
		 * 3������java�ķ�����ƽ��е���
		 */
		this.request = (HttpServletRequest)request;
		this.response = (HttpServletResponse)response;
		
		String uri = this.request.getRequestURI();
		
		/**
		 *   --/heima6servlet/HelloWorldAction.action
		 * 	 ת����HelloWorldAction
		 */
		String actionName = ServletUtils.convert(uri);
		
		
		//����java�ķ�����Ƶ��ø÷���
		try {
			Action action = (Action)Class.forName("cn.heima6.action."+actionName).newInstance();
			//forwardָ��Ҫ��ת����jspҳ��
			String forward = action.execute(this.request, this.response);

			/*
			 * ��ת�ķ���
			 * 	* �ض���
			 * 		request��Ĳ�����������
			 * 		this.response.sendRedirect(jspҳ��);
			 * 	* ת��
			 * 		����request���������ֵ����ȡ����
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
