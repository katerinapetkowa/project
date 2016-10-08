package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Post;
import model.PostsManager;


@WebServlet("/DislikeServlet")
public class DislikeServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int postId=Integer.parseInt(request.getParameter("post_id"));
		request.setAttribute("post_id", postId);
		String logged =(String) request.getSession().getAttribute("loggedAs");
		Post post = PostsManager.getInstance().getPost(postId);
		PostsManager.getInstance().downVotePost(logged,postId);
		RequestDispatcher view = request.getRequestDispatcher("DetailsPostServlet?post_id="+postId);
		view.forward(request, response);
	}

	

}
