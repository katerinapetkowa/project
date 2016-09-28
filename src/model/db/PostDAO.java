package model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import model.Post;
import model.UsersManager;

public class PostDAO {
	
	private static PostDAO instance;
	
	
	public synchronized static PostDAO getInstance(){
		if(instance == null){
			instance = new PostDAO();
		}
		return instance;
	}
	
	public Set<Post> getAllPostsFromDB(){
		Set<Post> posts = new HashSet<Post>();
		try {
			Statement st = DBManager.getInstance().getConnection().createStatement();
			ResultSet resultSet = st
					.executeQuery("SELECT post_id, user_id, category_name, title, points, upload_date, post_picture "
							+ "FROM posts P JOIN categories C ON P.category_id = C.category_id;");
			while (resultSet.next()) {
				posts.add(new Post(resultSet.getInt("post_id"), resultSet.getInt("user_id"),
						resultSet.getString("category_name"), resultSet.getString("title"), resultSet.getInt("points"),
						resultSet.getTimestamp("upload_date").toLocalDateTime(),resultSet.getString("post_picture")));
			}
			resultSet.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("Oops, cannot select all posts from db.");
			return posts;
		}
		System.out.println("Posts from db selected successfully");
		return posts;
	}
	
	
	public Set<Post> getPostsByUserFromDB(int userId){
		Set<Post> postsByUser = new HashSet<>();
		try{
		String query = "SELECT post_id, user_id, category_name, title, points, upload_date, post_picture "
				+ "FROM posts P JOIN categories C ON P.category_id = C.category_id WHERE user_id= " + userId + ";";
		Statement st = DBManager.getInstance().getConnection().createStatement();
		ResultSet resultSet = st.executeQuery(query);
		while (resultSet.next()) {
			//if(resultSet.getInt("user_id") == userId){
			postsByUser.add(new Post(resultSet.getInt("post_id"), resultSet.getInt("user_id"),
					resultSet.getString("category_name"), resultSet.getString("title"), resultSet.getInt("points"),
					resultSet.getTimestamp("upload_date").toLocalDateTime(),resultSet.getString("post_picture")));
			//}
		}
		resultSet.close();
		st.close();
		} catch(SQLException e) {
			System.out.println("Oops, cannot select posts of the user.");
			//e.printStackTrace();
			return postsByUser;
		}
		System.out.println("Posts of user loaded successfully");
		return postsByUser;
	}

	
	public int addPostToDB(String username, String category, String title, String picture){
		int userId = UsersManager.getInstance().getUser(username).getUserId();
		int postId = 0;
		try {
			PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(
					"INSERT INTO posts (user_id, category_id, title, points, post_picture) VALUES (?, (SELECT category_id FROM categories WHERE category_name ='"+ category+"'), ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, userId);
			st.setString(2, title);
			st.setInt(3, 0);
			st.setString(4, picture);
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
				postId = rs.getInt(1);
			}
			rs.close();
			st.close();
			System.out.println("Post added successfully to db");
		} catch (SQLException e) {
			System.out.println("Oops .. did not save the post in db");
			e.printStackTrace();
		}
		return postId;
	}
	
	public LocalDateTime getUploadDate(int postId){
		LocalDateTime uploadDate = LocalDateTime.now();
		try {
			Statement st = DBManager.getInstance().getConnection().createStatement();
			ResultSet resultSet = st
					.executeQuery("SELECT upload_date FROM posts WHERE post_id =" + postId +";");
			uploadDate = resultSet.getTimestamp("upload_date").toLocalDateTime();
			resultSet.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("Oops, did not get the upload date of post.");
			return uploadDate;
		}
		return uploadDate;
	}
	
	
	public String getUsernameOfPostUser(int userId){
		String username = "";
		try {
			Statement st = DBManager.getInstance().getConnection().createStatement();
			ResultSet resultSet = st
					.executeQuery("SELECT username FROM users WHERE user_id =" + userId +";");
			username = resultSet.getString("username");
			resultSet.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("Oops, did not get the username.");
			return username;
		}
		return username;
	}
	
	public void changePointsInDB(int postId, int points){
		try {
			PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(
					"UPDATE posts SET points = ? WHERE post_id = "
							+ postId + ";");
			st.setInt(1, points);
			st.executeUpdate();
			st.close();
			System.out.println("Points changed successfully in db");
		} catch (SQLException e) {
			System.out.println("Oops .. did not change the name of the user");
			e.printStackTrace();
		}
	}
	

}
