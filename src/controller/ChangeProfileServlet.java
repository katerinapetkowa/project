package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


import model.UsersManager;

@WebServlet("/ChangeProfileServlet")
@MultipartConfig
public class ChangeProfileServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String logged = (String) request.getSession().getAttribute("loggedAs");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String description = request.getParameter("description");
		Part profilePicture = request.getPart("profilePicture");// handles
																// data
																// from
																// <input
																// type=file
																// name=profilePic>
		if(!profilePicture.getContentType().split("/")[1].equals("octet-stream")){
		InputStream profilePicStream = profilePicture.getInputStream();
		// TODO validate data and modify valid property accordingly
		boolean valid = true;
		if (valid) {
			File dir = new File("userProfilePics");
			if (!dir.exists()) {
				dir.mkdir();
			}
			File profilePicFile = new File(dir,
					logged + "-profile-pic." + profilePicture.getContentType().split("/")[1]);
			if (profilePicFile.exists()) {
				profilePicFile.delete();
			}
			profilePicFile = new File(dir, logged + "-profile-pic." + profilePicture.getContentType().split("/")[1]);
			System.out.println("Try to save file with name: " + profilePicFile.getName());
			System.out.println("abs. path = " + profilePicFile.getAbsolutePath());
			Files.copy(profilePicStream, profilePicFile.toPath());
			UsersManager.getInstance().changeProfilePicture(logged, profilePicFile.getName());
		}
		}
			UsersManager.getInstance().changeProfile(logged, name, email, description);
			response.setHeader("Pragma", "No-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-Control", "no-cache");
			RequestDispatcher view = request.getRequestDispatcher("Profile.jsp");
			view.forward(request, response);
		
	}

}
