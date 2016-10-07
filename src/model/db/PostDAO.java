package model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Post;

public class PostDAO {

	private static PostDAO instance;

	public synchronized static PostDAO getInstance() {
		if (instance == null) {
			instance = new PostDAO();
		}
		return instance;
	}

	public Set<Post> getAllPostsFromDB() {
		Set<Post> posts = new HashSet<Post>();

		try {
			Statement st = DBManager.getInstance().getConnection().createStatement();
			ResultSet resultSet = st
					.executeQuery("SELECT post_id, username, category_name, title, points, upload_date, post_picture "
							+ "FROM posts P JOIN categories C ON P.category_id = C.category_id;");
			while (resultSet.next()) {
				posts.add(new Post(resultSet.getInt("post_id"), resultSet.getString("username"),
						resultSet.getString("category_name"), resultSet.getString("title"), resultSet.getInt("points"),
						resultSet.getTimestamp("upload_date").toLocalDateTime(), resultSet.getString("post_picture")));
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

	public Map<Integer, Post> getPostsByUserFromDB(String username) {
		Map<Integer, Post> postsByUser = new HashMap<>();
		try {
			String query = "SELECT post_id, username, category_name, title, points, upload_date, post_picture "
					+ "FROM posts P JOIN categories C ON P.category_id = C.category_id WHERE username= " + username + ";";
			Statement st = DBManager.getInstance().getConnection().createStatement();
			ResultSet resultSet = st.executeQuery(query);
			while (resultSet.next()) {
				postsByUser.put(resultSet.getInt("post_id"),
						new Post(resultSet.getInt("post_id"), resultSet.getString("username"),
								resultSet.getString("category_name"), resultSet.getString("title"),
								resultSet.getInt("points"), resultSet.getTimestamp("upload_date").toLocalDateTime(),
								resultSet.getString("post_picture")));
			}
			resultSet.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("Oops, cannot select posts of the user.");
			// e.printStackTrace();
			return postsByUser;
		}
		System.out.println("Posts of user loaded successfully");
		return postsByUser;
	}

	public int addPostToDB(String username, String category, String title, LocalDateTime uploadDate, String picture) {
		int postId = 0;
		try {
			PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(
					"INSERT INTO posts (username, category_id, title, points, upload_date, post_picture) VALUES (?, (SELECT category_id FROM categories WHERE category_name ='"
							+ category + "'), ?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, username);
			st.setString(2, title);
			st.setInt(3, 0);
			st.setTimestamp(4, Timestamp.valueOf(uploadDate));
			st.setString(5, picture);
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

//	public String getUsernameOfPostUser(int userId) {
//		String username = "";
//		try {
//			Statement st = DBManager.getInstance().getConnection().createStatement();
//			ResultSet resultSet = st.executeQuery("SELECT username FROM users WHERE user_id =" + userId + ";");
//			username = resultSet.getString("username");
//			resultSet.close();
//			st.close();
//		} catch (SQLException e) {
//			System.out.println("Oops, did not get the username.");
//			return username;
//		}
//		return username;
//	}

	public void changePointsInDB(int postId, int points) {
		// try {
		// PreparedStatement st =
		// DBManager.getInstance().getConnection().prepareStatement(
		// "UPDATE posts SET points = ? WHERE post_id = "
		// + postId + ";");
		// st.setInt(1, points);
		// st.executeUpdate();
		// st.close();
		// System.out.println("Points changed successfully in db");
		// } catch (SQLException e) {
		// System.out.println("Oops .. did not change the points of the post");
		// e.printStackTrace();
		// }
	}

	public void deletePostFromDB(int postId) {
		PreparedStatement deleteComments = null;
		PreparedStatement deletePost = null;
		try {
			DBManager.getInstance().getConnection().setAutoCommit(false);
			deleteComments = DBManager.getInstance().getConnection()
					.prepareStatement("DELETE FROM comments  WHERE post_id = ? ;");
			deleteComments.setInt(1, postId);
			deleteComments.executeUpdate();
			deletePost = DBManager.getInstance().getConnection()
					.prepareStatement("DELETE FROM posts  WHERE post_id = ? ;");
			deletePost.setInt(1, postId);
			deletePost.executeUpdate();
			DBManager.getInstance().getConnection().commit();
			System.out.println("Post deleted successfully from db");
		} catch (SQLException e) {
			try {
				System.err.print("Transaction is being rolled back, could not delete post");
				DBManager.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Oops .. did not delete the post from db");
			e.printStackTrace();
		} finally {
			try {
				if (deleteComments != null) {
					deleteComments.close();
				}
				if (deletePost != null) {
					deletePost.close();
				}
				DBManager.getInstance().getConnection().setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
