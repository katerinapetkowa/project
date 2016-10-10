package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CommentsManager;


@WebServlet("/WriteCommentServlet")
public class WriteCommentServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String comment = request.getParameter("comment");
		
		int postId = Integer.parseInt(request.getParameter("post_id"));
		String html = "";
		if(comment!=null){
			CommentsManager.getInstance().uploadComment(username, postId, comment, 0 , LocalDateTime.now());
			html="CommentsPage.jsp";
		}
		RequestDispatcher view = request.getRequestDispatcher(html);
		view.forward(request, response);
	
		
	}


}
