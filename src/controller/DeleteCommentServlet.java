package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CommentsManager;
import model.UsersManager;


@WebServlet("/DeleteCommentServlet")
public class DeleteCommentServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		int postId = Integer.parseInt(request.getParameter("post_id"));
		int commentId = Integer.parseInt(request.getParameter("comment_id"));
		CommentsManager.getInstance().deleteComment(username, postId, commentId);
		RequestDispatcher view = request.getRequestDispatcher("CommentsPage.jsp");
		view.forward(request, response);
	}

}
