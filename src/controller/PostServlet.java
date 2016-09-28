package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Post;
import model.PostsManager;
import model.User;
import model.UsersManager;
import model.db.PostDAO;
import model.db.UserDAO;

@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {
	
	public static void returnPostPic(Post post,  HttpServletResponse response) throws IOException{

		
		
		File postPicFile = new File("postPics", post.getPicture());
		response.setContentLength((int)postPicFile.length());
		String contentType = "image/"+postPicFile.getName().split("[.]")[postPicFile.getName().split("[.]").length-1];
		response.setContentType(contentType);
		OutputStream out = response.getOutputStream();
		Files.copy(postPicFile.toPath(), out);
	}
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		int postId = Integer.parseInt(request.getParameter("post_id"));
		if(PostsManager.getInstance().getAllPosts().containsKey(postId)){
			Post post = PostsManager.getInstance().getPost(postId);
			returnPostPic(post, response);
		}
		String logged = (String) request.getSession().getAttribute("loggedAs");
		if(logged == null){//session is new or expired
			System.out.println("This should not happen right now. Might happen later on other pages");
		}
		else{
			User user = UsersManager.getInstance().getUser(logged);
			Post post = PostsManager.getInstance().getPost(postId);
			File postPicFile = new File("postPics", post.getPicture());
			returnPostPic(post, response);
			
		}
	}
}

