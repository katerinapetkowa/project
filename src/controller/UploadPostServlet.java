package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.PostsManager;
import model.UsersManager;
import model.db.UserDAO;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/UploadPostServlet")
@MultipartConfig
public class UploadPostServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String html;
		String logged =(String) request.getSession().getAttribute("loggedAs");
		String title = request.getParameter("title");
		String category = request.getParameter("category");
		Part postPicture = request.getPart("postPicture");//handles data from <input type=file name=profilePic>
		InputStream postPicStream = postPicture.getInputStream();
		
		//TODO validate data and modify valid property accordingly
		boolean valid = true;
		if(valid){
			File dir = new File("postPics");
			if(!dir.exists()){
				dir.mkdir();
			}
			
			String date = DateTimeFormatter.ofPattern("MM-dd-yyyy").format(LocalDate.now());
			String time = DateTimeFormatter.ofPattern("H-mm-ss").format(LocalTime.now());
			File postPicFile = new File(dir, logged + "-" + date + "-" + time + "-post-pic."+ postPicture.getContentType().split("/")[1]);
			System.out.println("Try to save file with name: " + postPicFile.getName());
			System.out.println("abs. path = " + postPicFile.getAbsolutePath());
			Files.copy(postPicStream, postPicFile.toPath());
			PostsManager.getInstance().uploadPost(logged, category, title, postPicFile.getName());
			html = "posts.jsp";
		}else{
			html = "index.html";	
		}
			response.setHeader("Pragma", "No-cache"); 
			response.setDateHeader("Expires", 0); 
			response.setHeader("Cache-Control", "no-cache"); 
			RequestDispatcher view = request.getRequestDispatcher(html);
			view.forward(request, response);
		
	}

}