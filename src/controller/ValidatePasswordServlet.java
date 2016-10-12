package controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UsersManager;


@WebServlet("/ValidatePasswordServlet")
public class ValidatePasswordServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String password = UsersManager.getInstance().passwordToMD5(request.getParameter("oldPassword"));
		String username = request.getSession().getAttribute("loggedAs").toString();
		ServletOutputStream out =response.getOutputStream();
		if(UsersManager.getInstance().getUser(username).getPassword().equals(password)){
			 out.println("t");
		}else {
			out.println("f");
		}
		System.out.println("change pass servlet");
		System.out.println(password);
	}

}
