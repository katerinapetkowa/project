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
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

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
					+ "FROM posts P JOIN categories C ON P.category_id = C.category_id WHERE username= ? ;";
			PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(query);
			ps.setString(1, username);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				postsByUser.put(resultSet.getInt("post_id"),
						new Post(resultSet.getInt("post_id"), resultSet.getString("username"),
								resultSet.getString("category_name"), resultSet.getString("title"),
								resultSet.getInt("points"), resultSet.getTimestamp("upload_date").toLocalDateTime(),
								resultSet.getString("post_picture")));
			}
			resultSet.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Oops, cannot select posts of the user.");
			// e.printStackTrace();
			return postsByUser;
		}
		System.out.println("Posts of user loaded successfully");
		return postsByUser;
	}

	public Map<Integer, HashSet<String>> getPostsUpvotesFromDB() {
		Map<Integer, HashSet<String>> posts = new HashMap<>();
		try {
			Statement st = DBManager.getInstance().getConnection().createStatement();
			ResultSet resultSet = st.executeQuery("SELECT post_id, username FROM post_upvotes;");
			while (resultSet.next()) {
				if (!posts.containsKey(resultSet.getInt("post_id"))) {
					posts.put(resultSet.getInt("post_id"), new HashSet<String>());
				}
				posts.get(resultSet.getInt("post_id")).add(resultSet.getString("username"));
			}
			resultSet.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("Oops, cannot select upvotes of the posts.");
			// e.printStackTrace();
			return posts;
		}
		System.out.println("Upvotes of posts loaded successfully");
		return posts;
	}

	public Map<Integer, HashSet<String>> getPostsDownvotesFromDB() {
		Map<Integer, HashSet<String>> posts = new HashMap<>();
		try {
			Statement st = DBManager.getInstance().getConnection().createStatement();
			ResultSet resultSet = st.executeQuery("SELECT post_id, username FROM post_downvotes;");
			while (resultSet.next()) {
				if (!posts.containsKey(resultSet.getInt("post_id"))) {
					posts.put(resultSet.getInt("post_id"), new HashSet<String>());
				}
				posts.get(resultSet.getInt("post_id")).add(resultSet.getString("username"));
			}
			resultSet.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("Oops, cannot select downvotes of the posts.");
			// e.printStackTrace();
			return posts;
		}
		System.out.println("Downvotes of posts loaded successfully");
		return posts;
	}

	public Map<Integer, Post> getPostsUpvotedByUserFromDB(String username) {
		Map<Integer, Post> likedPosts = new HashMap<>();
		try {
			PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(
					"SELECT U.post_id, U.username, category_name, title, points, upload_date, post_picture "
							+ "FROM post_upvotes U JOIN posts P ON U.post_id = P.post_id JOIN categories C ON C.category_id = P.category_id "
							+ "WHERE U.username = ? ;");
			st.setString(1, username);
			ResultSet resultSet = st.executeQuery();
			while (resultSet.next()) {
				likedPosts.put(resultSet.getInt("post_id"),
						new Post(resultSet.getInt("post_id"), resultSet.getString("username"),
								resultSet.getString("category_name"), resultSet.getString("title"),
								resultSet.getInt("points"), resultSet.getTimestamp("upload_date").toLocalDateTime(),
								resultSet.getString("post_picture")));
			}
			resultSet.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("Oops, cannot select upvoted posts of the user.");
			// e.printStackTrace();
			return likedPosts;
		}
		System.out.println("Upvoted posts of user loaded successfully");
		return likedPosts;
	}

	public Set<Integer> getPostDownvotesByUser(String username) {
		Set<Integer> downvotedPosts = new ConcurrentSkipListSet<>();
		try {
			PreparedStatement st = DBManager.getInstance().getConnection()
					.prepareStatement("SELECT post_id FROM post_downvotes WHERE username = ? ;");
			st.setString(1, username);
			ResultSet resultSet = st.executeQuery();
			while (resultSet.next()) {
				downvotedPosts.add(resultSet.getInt("post_id"));
			}
			resultSet.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("Oops, cannot select downvoted posts of the user.");
			// e.printStackTrace();
			return downvotedPosts;
		}
		System.out.println("Downvoted posts of user loaded successfully");
		return downvotedPosts;
	}

	public Map<Integer, Post> getPostsCommentedByUserFromDB(String username) {
		Map<Integer, Post> commentedPosts = new HashMap<>();
		try {
			PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(
					"SELECT P.post_id, P.username, category_name, title, P.points, P.upload_date, P.post_picture FROM posts P JOIN categories C ON P.category_id = C.category_id JOIN comments com ON com.post_id = P.post_id WHERE com.username = ? ;");
			st.setString(1, username);
			ResultSet resultSet = st.executeQuery();
			while (resultSet.next()) {
				commentedPosts.put(resultSet.getInt("post_id"),
						new Post(resultSet.getInt("post_id"), resultSet.getString("username"),
								resultSet.getString("category_name"), resultSet.getString("title"),
								resultSet.getInt("points"), resultSet.getTimestamp("upload_date").toLocalDateTime(),
								resultSet.getString("post_picture")));
			}
			resultSet.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("Oops, cannot select commented posts of the user.");
			// e.printStackTrace();
			return commentedPosts;
		}
		System.out.println("Commented posts of user loaded successfully");
		return commentedPosts;
	}

	public Map<Integer, Set<Integer>> getCommentsOfOtherPostsOfUser(String username) {
		Map<Integer, Set<Integer>> commentedPosts = new HashMap<>();
		try {
			PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(
					"SELECT c.post_id, c.comment_id FROM posts p JOIN comments c ON p.post_id = c.post_id WHERE p.username != ? ;");
			st.setString(1, username);
			ResultSet resultSet = st.executeQuery();
			while (resultSet.next()) {
				if (!commentedPosts.containsKey(resultSet.getInt("post_id"))) {
					commentedPosts.put(resultSet.getInt("post_id"), new TreeSet<Integer>());
				}
				commentedPosts.get(resultSet.getInt("post_id")).add(resultSet.getInt("comment_id"));
			}
			resultSet.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("Oops, cannot select comments of other posts of the user.");
			// e.printStackTrace();
			return commentedPosts;
		}
		System.out.println("Comments of other posts of user loaded successfully");
		return commentedPosts;
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

	public void upvotePostInDB(String username, int postId) {
		PreparedStatement selectPoints = null;
		PreparedStatement changePoints = null;
		PreparedStatement addUpvote = null;
		int points = 0;
		try {
			DBManager.getInstance().getConnection().setAutoCommit(false);
			selectPoints = DBManager.getInstance().getConnection()
					.prepareStatement("SELECT points FROM posts  WHERE post_id = ? ;");
			selectPoints.setInt(1, postId);
			ResultSet rs = selectPoints.executeQuery();
			if (rs.next()) {
				points = rs.getInt(1);
				System.out.println("Points before upvoting - " + points);
			}
			changePoints = DBManager.getInstance().getConnection()
					.prepareStatement("UPDATE posts SET points = ?  WHERE post_id = ? ;");
			changePoints.setInt(1, (points + 1));
			changePoints.setInt(2, postId);
			changePoints.executeUpdate();
			addUpvote = DBManager.getInstance().getConnection()
					.prepareStatement("INSERT INTO post_upvotes (post_id, username) VALUES (?, ?) ;");
			addUpvote.setInt(1, postId);
			addUpvote.setString(2, username);
			addUpvote.executeUpdate();
			DBManager.getInstance().getConnection().commit();
			System.out.println("Post upvoted successfully in db");
		} catch (SQLException e) {
			try {
				System.err.print("Transaction is being rolled back, could not upvote post");
				DBManager.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Oops .. did not upvote the post in db");
			e.printStackTrace();
		} finally {
			try {
				if (selectPoints != null) {
					selectPoints.close();
				}
				if (changePoints != null) {
					changePoints.close();
				}
				if (addUpvote != null) {
					addUpvote.close();
				}
				DBManager.getInstance().getConnection().setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void downvotePostInDB(String username, int postId) {
		PreparedStatement selectPoints = null;
		PreparedStatement changePoints = null;
		PreparedStatement addDownvote = null;
		int points = 0;
		try {
			DBManager.getInstance().getConnection().setAutoCommit(false);
			selectPoints = DBManager.getInstance().getConnection()
					.prepareStatement("SELECT points FROM posts  WHERE post_id = ? ;");
			selectPoints.setInt(1, postId);
			ResultSet rs = selectPoints.executeQuery();
			if (rs.next()) {
				points = rs.getInt(1);
				System.out.println("Points before downvoting - " + points);
			}
			changePoints = DBManager.getInstance().getConnection()
					.prepareStatement("UPDATE posts SET points = ?  WHERE post_id = ? ;");
			changePoints.setInt(1, points - 1);
			changePoints.setInt(2, postId);
			changePoints.executeUpdate();
			addDownvote = DBManager.getInstance().getConnection()
					.prepareStatement("INSERT INTO post_downvotes (post_id, username) VALUES (?, ?) ;");
			addDownvote.setInt(1, postId);
			addDownvote.setString(2, username);
			addDownvote.executeUpdate();
			DBManager.getInstance().getConnection().commit();
			System.out.println("Post downvoted successfully in db");
		} catch (SQLException e) {
			try {
				System.err.print("Transaction is being rolled back, could not downvote post");
				DBManager.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Oops .. did not downvote the post in db");
			e.printStackTrace();
		} finally {
			try {
				if (selectPoints != null) {
					selectPoints.close();
				}
				if (changePoints != null) {
					changePoints.close();
				}
				if (addDownvote != null) {
					addDownvote.close();
				}
				DBManager.getInstance().getConnection().setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void reverseUpvoteInDB(String username, int postId) {
		PreparedStatement selectPoints = null;
		PreparedStatement changePoints = null;
		PreparedStatement deleteUpvote = null;
		int points = 0;
		try {
			DBManager.getInstance().getConnection().setAutoCommit(false);
			selectPoints = DBManager.getInstance().getConnection()
					.prepareStatement("SELECT points FROM posts  WHERE post_id = ? ;");
			selectPoints.setInt(1, postId);
			ResultSet rs = selectPoints.executeQuery();
			if (rs.next()) {
				points = rs.getInt(1);
				System.out.println("Points before reversing upvote - " + points);
			}
			changePoints = DBManager.getInstance().getConnection()
					.prepareStatement("UPDATE posts SET points = ?  WHERE post_id = ? ;");
			changePoints.setInt(1, points - 1);
			changePoints.setInt(2, postId);
			changePoints.executeUpdate();
			deleteUpvote = DBManager.getInstance().getConnection()
					.prepareStatement("DELETE FROM post_upvotes WHERE post_id = ? AND username = ?");
			deleteUpvote.setInt(1, postId);
			deleteUpvote.setString(2, username);
			deleteUpvote.executeUpdate();
			DBManager.getInstance().getConnection().commit();
			System.out.println("Upvote deleted successfully in db");
		} catch (SQLException e) {
			try {
				System.err.print("Transaction is being rolled back, could not reverse upvote of post");
				DBManager.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Oops .. did not reverse upvote the post in db");
			e.printStackTrace();
		} finally {
			try {
				if (selectPoints != null) {
					selectPoints.close();
				}
				if (changePoints != null) {
					changePoints.close();
				}
				if (deleteUpvote != null) {
					deleteUpvote.close();
				}
				DBManager.getInstance().getConnection().setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void reverseDownvoteInDB(String username, int postId) {
		PreparedStatement selectPoints = null;
		PreparedStatement changePoints = null;
		PreparedStatement deleteDownvote = null;
		int points = 0;
		try {
			DBManager.getInstance().getConnection().setAutoCommit(false);
			selectPoints = DBManager.getInstance().getConnection()
					.prepareStatement("SELECT points FROM posts  WHERE post_id = ? ;");
			selectPoints.setInt(1, postId);
			ResultSet rs = selectPoints.executeQuery();
			if (rs.next()) {
				points = rs.getInt(1);
				System.out.println("Points before reversing downvote - " + points);
			}
			changePoints = DBManager.getInstance().getConnection()
					.prepareStatement("UPDATE posts SET points = ?  WHERE post_id = ? ;");
			changePoints.setInt(1, points + 1);
			changePoints.setInt(2, postId);
			changePoints.executeUpdate();
			deleteDownvote = DBManager.getInstance().getConnection()
					.prepareStatement("DELETE FROM post_downvotes WHERE post_id = ? AND username = ?");
			deleteDownvote.setInt(1, postId);
			deleteDownvote.setString(2, username);
			deleteDownvote.executeUpdate();
			DBManager.getInstance().getConnection().commit();
			System.out.println("Downvote deleted successfully in db");
		} catch (SQLException e) {
			try {
				System.err.print("Transaction is being rolled back, could not reverse downvote of post");
				DBManager.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Oops .. did not reverse downvote the post in db");
			e.printStackTrace();
		} finally {
			try {
				if (selectPoints != null) {
					selectPoints.close();
				}
				if (changePoints != null) {
					changePoints.close();
				}
				if (deleteDownvote != null) {
					deleteDownvote.close();
				}
				DBManager.getInstance().getConnection().setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void upvoteToDownvteInDB(String username, int postId) {
		PreparedStatement selectPoints = null;
		PreparedStatement changePoints = null;
		PreparedStatement deleteUpvote = null;
		PreparedStatement addDownvote = null;
		int points = 0;
		try {
			DBManager.getInstance().getConnection().setAutoCommit(false);
			selectPoints = DBManager.getInstance().getConnection()
					.prepareStatement("SELECT points FROM posts  WHERE post_id = ? ;");
			selectPoints.setInt(1, postId);
			ResultSet rs = selectPoints.executeQuery();
			if (rs.next()) {
				points = rs.getInt(1);
				System.out.println("Points before changing from upvote to downvote - " + points);
			}
			changePoints = DBManager.getInstance().getConnection()
					.prepareStatement("UPDATE posts SET points = ?  WHERE post_id = ? ;");
			changePoints.setInt(1, points - 2);
			changePoints.setInt(2, postId);
			changePoints.executeUpdate();
			deleteUpvote = DBManager.getInstance().getConnection()
					.prepareStatement("DELETE FROM post_upvotes WHERE post_id = ? AND username = ?");
			deleteUpvote.setInt(1, postId);
			deleteUpvote.setString(2, username);
			deleteUpvote.executeUpdate();
			addDownvote = DBManager.getInstance().getConnection()
					.prepareStatement("INSERT INTO post_downvotes (post_id, username) VALUES (?, ?) ;");
			addDownvote.setInt(1, postId);
			addDownvote.setString(2, username);
			addDownvote.executeUpdate();
			DBManager.getInstance().getConnection().commit();
			System.out.println("Downvote deleted successfully in db");
		} catch (SQLException e) {
			try {
				System.err.print("Transaction is being rolled back, could not reverse downvote of post");
				DBManager.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Oops .. did not reverse downvote the post in db");
			e.printStackTrace();
		} finally {
			try {
				if (selectPoints != null) {
					selectPoints.close();
				}
				if (changePoints != null) {
					changePoints.close();
				}
				if (deleteUpvote != null) {
					deleteUpvote.close();
				}
				if (addDownvote != null) {
					addDownvote.close();
				}
				DBManager.getInstance().getConnection().setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void downvoteToUpvoteInDB(String username, int postId) {
		PreparedStatement selectPoints = null;
		PreparedStatement changePoints = null;
		PreparedStatement deleteDownvote = null;
		PreparedStatement addUpvote = null;
		int points = 0;
		try {
			DBManager.getInstance().getConnection().setAutoCommit(false);
			selectPoints = DBManager.getInstance().getConnection()
					.prepareStatement("SELECT points FROM posts  WHERE post_id = ? ;");
			selectPoints.setInt(1, postId);
			ResultSet rs = selectPoints.executeQuery();
			if (rs.next()) {
				points = rs.getInt(1);
				System.out.println("Points before changing from downvote to upvote - " + points);
			}
			changePoints = DBManager.getInstance().getConnection()
					.prepareStatement("UPDATE posts SET points = ?  WHERE post_id = ? ;");
			changePoints.setInt(1, points + 2);
			changePoints.setInt(2, postId);
			changePoints.executeUpdate();
			deleteDownvote = DBManager.getInstance().getConnection()
					.prepareStatement("DELETE FROM post_downvotes WHERE post_id = ? AND username = ?");
			deleteDownvote.setInt(1, postId);
			deleteDownvote.setString(2, username);
			deleteDownvote.executeUpdate();
			addUpvote = DBManager.getInstance().getConnection()
					.prepareStatement("INSERT INTO post_upvotes (post_id, username) VALUES (?, ?) ;");
			addUpvote.setInt(1, postId);
			addUpvote.setString(2, username);
			addUpvote.executeUpdate();
			DBManager.getInstance().getConnection().commit();
			System.out.println("Downvote deleted successfully in db");
		} catch (SQLException e) {
			try {
				System.err.print("Transaction is being rolled back, could not reverse downvote of post");
				DBManager.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Oops .. did not reverse downvote the post in db");
			e.printStackTrace();
		} finally {
			try {
				if (selectPoints != null) {
					selectPoints.close();
				}
				if (changePoints != null) {
					changePoints.close();
				}
				if (deleteDownvote != null) {
					deleteDownvote.close();
				}
				if (addUpvote != null) {
					addUpvote.close();
				}
				DBManager.getInstance().getConnection().setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deletePostFromDB(int postId) {
		PreparedStatement deleteComments = null;
		PreparedStatement deletePost = null;
		PreparedStatement deleteUpvotes = null;
		PreparedStatement deleteDownvotes = null;
		try {
			DBManager.getInstance().getConnection().setAutoCommit(false);
			// delete comments of post
			deleteComments = DBManager.getInstance().getConnection()
					.prepareStatement("DELETE FROM comments  WHERE post_id = ? ;");
			deleteComments.setInt(1, postId);
			deleteComments.executeUpdate();
			// delete upvotes of post
			deleteUpvotes = DBManager.getInstance().getConnection()
					.prepareStatement("DELETE FROM post_upvotes WHERE post_id = ? ;");
			deleteUpvotes.setInt(1, postId);
			deleteUpvotes.executeUpdate();
			// delete downvotes of post
			deleteDownvotes = DBManager.getInstance().getConnection()
					.prepareStatement("DELETE FROM post_downvotes WHERE post_id = ? ");
			deleteDownvotes.setInt(1, postId);
			deleteDownvotes.executeUpdate();
			// delete post
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
