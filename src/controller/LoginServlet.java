package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UsersManager;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String htmlFile;
		if(UsersManager.getInstance().validLogin(username, password)){
			request.getSession().setAttribute("loggedAs", username);
			htmlFile = "posts.jsp";
		}else{
			htmlFile = "LoginFailed.html";
		}
		response.setHeader("Pragma", "No-cache"); 
		response.setDateHeader("Expires", 0); 
		response.setHeader("Cache-Control", "no-cache"); 
		RequestDispatcher view = request.getRequestDispatcher(htmlFile);
		view.forward(request, response);
	}

}
