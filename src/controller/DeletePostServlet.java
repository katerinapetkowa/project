package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PostsManager;


@WebServlet("/DeletePostServlet")
public class DeletePostServlet extends HttpServlet {
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		int postId = Integer.parseInt(request.getParameter("post_id"));
		PostsManager.getInstance().deletePost(postId);
		RequestDispatcher view = request.getRequestDispatcher("posts.jsp");
		view.forward(request, response);
	}

}
