package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.UsersManager;
import model.db.UserDAO;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
@MultipartConfig
public class RegisterServlet extends HttpServlet {
	//private static final String PASSWORD_PATTERN =
           // "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String html;
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		Part profilePicture = request.getPart("profilePicture");//handles data from <input type=file name=profilePic>
		InputStream profilePicStream = profilePicture.getInputStream();
		//Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		//Matcher matcher = pattern.matcher(password);
		//if((matcher.matches())&&(!password.isEmpty()) && (password.equals(password2))){
		//TODO validate data and modify valid property accordingly
		
		if(UsersManager.getInstance().validUsername(username)){
			
		
				boolean valid = true;
				if(valid){
						File dir = new File("userProfilePics");
						if(!dir.exists()){
							dir.mkdir();
						}
							File profilePicFile = new File(dir, username+"-profile-pic."+ profilePicture.getContentType().split("/")[1]);
							if (profilePicFile.exists()) {
								profilePicFile.delete();
							}
							System.out.println("Try to save file with name: " + profilePicFile.getName());
							System.out.println("abs. path = " + profilePicFile.getAbsolutePath());
							Files.copy(profilePicStream, profilePicFile.toPath());
							String encryptedPassword = UsersManager.getInstance().passwordToMD5(password2);
							UsersManager.getInstance().registerUser(username, name, encryptedPassword, email, profilePicFile.getName());
							
						//html = " index.html";
					//}
					//else{
						//html = " RegisterFailed.html";
					//}
						
				}	
				html = "index.html";	
		}else{
			html = "RegisterFailed.html";
		}
		RequestDispatcher view = request.getRequestDispatcher(html);
		view.forward(request, response);
	}

}
