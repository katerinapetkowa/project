package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CommentDAO {

	private static CommentDAO instance;
	//TODO nqkvi tupi kolekcii probably po post id nqkuv shiban map
	
	public synchronized static CommentDAO getInstance(){
		if(instance == null){
			instance = new CommentDAO();
		}
		return instance;
	}
	
	public void getAllCommentsOfPost(int postId){
		try {
			Statement st = DBManager.getInstance().getConnection().createStatement();
			ResultSet resultSet = st
					.executeQuery("SELECT comment_id, user_id, post_id, text, points, upload_date FROM users;");
			
		} catch (SQLException e) {
			System.out.println("Oops, cannot make statement.");
		}
	}
	
	
}
