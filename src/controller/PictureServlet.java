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

import model.User;
import model.UsersManager;;

@WebServlet("/PictureServlet")
public class PictureServlet extends HttpServlet {
	
	public static void returnProfilePic(User u,  HttpServletResponse response) throws IOException{

		File profilePicFile = new File("userProfilePics", u.getProfilePicture());
		response.setContentLength((int)profilePicFile.length());
		String contentType = "image/"+profilePicFile.getName().split("[.]")[profilePicFile.getName().split("[.]").length-1];
		response.setContentType(contentType);
		OutputStream out = response.getOutputStream();
		Files.copy(profilePicFile.toPath(), out);
	}
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestedUsername = request.getParameter("username");
		if(requestedUsername != null){
			User user = UsersManager.getInstance().getUser(requestedUsername);
			returnProfilePic(user, response);
		}
		String logged = (String) request.getSession().getAttribute("loggedAs");
		if(logged == null){//session is new or expired
			System.out.println("This should not happen right now. Might happen later on other pages");
		}
		else{
			User user = UsersManager.getInstance().getUser(logged);
			File profilePicFile = new File("userProfilePics", user.getProfilePicture());
			returnProfilePic(user, response);
			
		}
	}
}
