package controller;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import model.PostsManager;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String title = request.getParameter("title");
		
		PostsManager.getInstance().searchPosts(title);
		request.setAttribute("title", title);
		String html = "search.jsp";
		RequestDispatcher view = request.getRequestDispatcher(html);
		view.forward(request, response);
		
	}

	

}
