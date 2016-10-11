package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UsersManager;


@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String logged = (String) request.getSession().getAttribute("loggedAs");
		String username = request.getParameter("username");
		UsersManager.getInstance().deleteUser(username);
		request.getSession().invalidate();
		response.setHeader("Pragma", "No-cache"); 
		response.setDateHeader("Expires", 0); 
		response.setHeader("Cache-Control", "no-cache"); 
		request.getRequestDispatcher("register.html").forward(request, response);
	}

}
