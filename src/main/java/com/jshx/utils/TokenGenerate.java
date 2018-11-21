package com.jshx.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenGenerate extends HttpServlet {
	private HashMap<String, String> hp = new HashMap();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request.
		String depid = request.getParameter("depid").toString();
		// String token = hp.get("402880e43d33d63e013d34199be70012").toString();
		String token = TokenUtil.crearteToken(depid);
		PrintWriter out = response.getWriter();
		out.println(token);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Put your code here
		doGet(request, response);
	}

	public void init() throws ServletException {
		hp.put("402880e43d33d63e013d34199be70012", "123456");
	}

	public void destroy() {
		super.destroy();
	}

	public TokenGenerate() {
		super();
	}

	public static void main(String[] args) {

		String deptId = "402880e43d33d63e013d34199be70012";

		String token1 = TokenUtil.crearteToken(deptId);
		System.out.println("token1 is   " + token1);

		String token2 = TokenUtil.getToken(deptId);
		System.out.println("token2 is   " + token2);

		String token3 = TokenUtil.getToken("111111111");
		System.out.println("token3 is   " + token3);

		try {
			Thread.sleep(30 * 60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String token4 = TokenUtil.getToken(deptId);
		System.out.println("token4 is   " + token4);

	}
}
