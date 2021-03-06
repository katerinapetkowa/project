package model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import model.Post;
import model.User;
import model.UsersManager;

public class UserDAO {

	private static UserDAO instance;

	public synchronized static UserDAO getInstance() {
		if (instance == null) {
			System.out.println("Instance is null, initializing with new dao");
			instance = new UserDAO();
		}
		System.out.println("Returning dao");
		return instance;
	}

	public Set<User> getAllUsersFromDB() {
		Set<User> users = new HashSet<User>();
		try {
			Statement st = DBManager.getInstance().getConnection().createStatement();
			ResultSet resultSet = st
					.executeQuery("SELECT username, name, password, email, profile_picture, description FROM users;");
			while (resultSet.next()) {
				String username = resultSet.getString("username");
				ConcurrentHashMap<Integer, Post> posts = new ConcurrentHashMap<>();
				posts.putAll(PostDAO.getInstance().getPostsByUserFromDB(username));
				ConcurrentHashMap<Integer, Post> upvotedPosts = new ConcurrentHashMap<>();
				upvotedPosts.putAll(PostDAO.getInstance().getPostsUpvotedByUserFromDB(username));
				ConcurrentHashMap<Integer, Post> commentedPosts = new ConcurrentHashMap<>();
				commentedPosts.putAll(PostDAO.getInstance().getPostsCommentedByUserFromDB(username));
				ConcurrentSkipListSet<Integer> downvotedPosts = new ConcurrentSkipListSet<>();
				downvotedPosts.addAll(PostDAO.getInstance().getPostDownvotesByUser(username));
				ConcurrentHashMap<Integer, Set<Integer>> comments = new ConcurrentHashMap<>();
				comments.putAll(PostDAO.getInstance().getCommentsOfOtherPostsOfUser(username));
				users.add(new User(resultSet.getString("username"), resultSet.getString("name"),
						resultSet.getString("password"), resultSet.getString("email"),
						resultSet.getString("profile_picture"), resultSet.getString("description"), posts, upvotedPosts,
						commentedPosts, comments, downvotedPosts));
			}
			resultSet.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("Oops, cannot get all users from db.");
			return users;
		}
		System.out.println("Users loaded successfully from db");
		return users;
	}

	public void addUserToDB(String username, String name, String password, String email, String profilePicture) {
		try {
			PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(
					"INSERT INTO users (username, name, password, email, profile_picture, description) VALUES (?, ?, ?, ?, ?, ?);");
			st.setString(1, username);
			st.setString(2, name);
			st.setString(3, password);
			st.setString(4, email);
			st.setString(5, profilePicture);
			st.setString(6, "My Funny Collection");
			st.executeUpdate();
			// ResultSet rs = st.getGeneratedKeys();
			// if (rs.next()) {
			// userId = rs.getInt(1);
			// }
			// rs.close();
			st.close();
			System.out.println("User added successfully to db");
		} catch (SQLException e) {
			System.out.println("Oops .. did not save the user in db");
			e.printStackTrace();
		}
	}

	// public void changeNameInDB(String username, String name) {
	// try {
	// PreparedStatement ps = DBManager.getInstance().getConnection()
	// .prepareStatement("UPDATE users SET name = ? WHERE username = ?;");
	// ps.setString(1, name);
	// ps.setString(2, username);
	// // Statement st =
	// // DBManager.getInstance().getConnection().createStatement();
	// // st.executeUpdate("UPDATE users SET name = '" + name + "' WHERE
	// // username = '" + username + "';");
	// // st.close();
	// ps.executeUpdate();
	// System.out.println("Name changed successfully in db");
	// } catch (SQLException e) {
	// System.out.println("Oops .. did not change the name of the user");
	// e.printStackTrace();
	// }
	// }

	public void changePasswordInDB(String username, String password) {
		PreparedStatement changePassword = null;
		try {
			changePassword = DBManager.getInstance().getConnection()
					.prepareStatement("UPDATE users SET password = ? WHERE username = ?;");
			changePassword.setString(1, password);
			changePassword.setString(2, username);
			changePassword.executeUpdate();
			System.out.println("Password changed successfully in db");
		} catch (SQLException e) {
			System.out.println("Oops .. did not change the password of the user");
			e.printStackTrace();
		} finally {
			try {
				if (changePassword != null) {
					changePassword.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// public void changeEmailInDB(String username, String email) {
	// try {
	// Statement st = DBManager.getInstance().getConnection().createStatement();
	// st.executeUpdate("UPDATE users SET email = '" + email + "' WHERE username
	// = '" + username + "';");
	// st.close();
	// System.out.println("Email changed succesfully in db");
	// } catch (SQLException e) {
	// System.out.println("Oops .. did not change the email of the user");
	// e.printStackTrace();
	// }
	// }

	public void changeProfilePictureInDB(String username, String profilePicture) {
		PreparedStatement changeProfilePicture = null;
		try {
			changeProfilePicture = DBManager.getInstance().getConnection()
					.prepareStatement("UPDATE users SET profile_picture = ? WHERE username = ?;");
			changeProfilePicture.setString(1, profilePicture);
			changeProfilePicture.setString(2, username);
			System.out.println("Profile picture changed succesfully in db");
		} catch (SQLException e) {
			System.out.println("Oops .. did not change the profile picture of the user");
			e.printStackTrace();
		} finally {
			try {
				if (changeProfilePicture != null) {
					changeProfilePicture.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void changeProfileInDB(String username, String name, String email, String description) {
		PreparedStatement changeProfile = null;
		try {
			changeProfile = DBManager.getInstance().getConnection()
					.prepareStatement("UPDATE users SET name = ?, email = ?, description = ? WHERE username = ?;");
			changeProfile.setString(1, name);
			changeProfile.setString(2, email);
			changeProfile.setString(3, description);
			changeProfile.setString(4, username);
			changeProfile.executeUpdate();
			System.out.println("Profile changed succesfully in db");
		} catch (SQLException e) {
			System.out.println("Oops .. did not change profile");
			e.printStackTrace();
		} finally {
			try {
				if (changeProfile != null) {
					changeProfile.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteUserFromDB(String username) {
		PreparedStatement selectUpvotedPosts = null;
		PreparedStatement changePointsOfPosts = null;
		PreparedStatement selectDownvotedPosts = null;
		PreparedStatement deleteUpvotesOfUser = null;
		PreparedStatement deleteDownvotesOfUser = null;
		PreparedStatement deleteUpvotesOfPosts = null;
		PreparedStatement deleteDownvotesOfPosts = null;
		PreparedStatement deleteCommentsOfUser = null;
		PreparedStatement deleteCommentsOfPost = null;
		PreparedStatement deletePosts = null;
		PreparedStatement deleteUser = null;
		try {
			DBManager.getInstance().getConnection().setAutoCommit(false);
			selectUpvotedPosts = DBManager.getInstance().getConnection()
					.prepareStatement("SELECT P.post_id, P.points FROM posts P JOIN post_upvotes U ON P.post_id = U.post_id WHERE U.username =?;");
			selectUpvotedPosts.setString(1, username);
			ResultSet rs = selectUpvotedPosts.executeQuery();
			changePointsOfPosts = DBManager.getInstance().getConnection()
					.prepareStatement("UPDATE posts SET points = ? WHERE post_id = ?;");
			while(rs.next()){
				changePointsOfPosts.setInt(1, rs.getInt("points") - 1);
				changePointsOfPosts.setInt(2, rs.getInt("post_id"));
				changePointsOfPosts.executeUpdate();
			}
			selectDownvotedPosts = DBManager.getInstance().getConnection()
					.prepareStatement("SELECT P.post_id, P.points FROM posts P JOIN post_downvotes D ON P.post_id = D.post_id WHERE D.username =?;");
			selectDownvotedPosts.setString(1, username);
			ResultSet rs1 = selectDownvotedPosts.executeQuery();
			while(rs1.next()){
				changePointsOfPosts.setInt(1, rs1.getInt("points") + 1);
				changePointsOfPosts.setInt(2, rs1.getInt("post_id"));
				changePointsOfPosts.executeUpdate();
			}
			deleteUpvotesOfUser = DBManager.getInstance().getConnection()
					.prepareStatement("DELETE FROM post_upvotes WHERE username = ? ;");
			deleteUpvotesOfUser.setString(1, username);
			deleteUpvotesOfUser.executeUpdate();
			deleteDownvotesOfUser = DBManager.getInstance().getConnection()
					.prepareStatement("DELETE FROM post_downvotes WHERE username = ? ;");
			deleteDownvotesOfUser.setString(1, username);
			deleteDownvotesOfUser.executeUpdate();
			deleteCommentsOfUser = DBManager.getInstance().getConnection()
					.prepareStatement("DELETE FROM comments  WHERE username = ? ;");
			deleteCommentsOfUser.setString(1, username);
			deleteCommentsOfUser.executeUpdate();
			if (!UsersManager.getInstance().getUser(username).getPosts().isEmpty()) {
				for (int i : UsersManager.getInstance().getUser(username).getPosts().keySet()) {
					deleteUpvotesOfPosts = DBManager.getInstance().getConnection()
							.prepareStatement("DELETE FROM post_upvotes WHERE post_id = ?;");
					deleteUpvotesOfPosts.setInt(1, i);
					deleteUpvotesOfPosts.executeUpdate();
					deleteDownvotesOfPosts = DBManager.getInstance().getConnection()
							.prepareStatement("DELETE FROM post_downvotes WHERE post_id = ?;");
					deleteDownvotesOfPosts.setInt(1, i);
					deleteDownvotesOfPosts.executeUpdate();
					deleteCommentsOfPost = DBManager.getInstance().getConnection()
							.prepareStatement("DELETE FROM comments  WHERE post_id = ? ;");
					deleteCommentsOfPost.setInt(1, i);
					deleteCommentsOfPost.executeUpdate();
				}
			}
			deletePosts = DBManager.getInstance().getConnection()
					.prepareStatement("DELETE FROM posts  WHERE username = ? ;");
			deletePosts.setString(1, username);
			deletePosts.executeUpdate();
			deleteUser = DBManager.getInstance().getConnection()
					.prepareStatement("DELETE FROM users  WHERE username = ? ;");
			deleteUser.setString(1, username);
			deleteUser.executeUpdate();
			DBManager.getInstance().getConnection().commit();
			System.out.println("User deleted successfully from db");
		} catch (SQLException e) {
			try {
				System.err.print("Transaction is being rolled back, could not delete user");
				DBManager.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Oops .. did not delete the user from db");
			e.printStackTrace();
		} finally {
			try {
				if (deleteCommentsOfUser != null) {
					deleteCommentsOfUser.close();
				}
				if (deleteCommentsOfPost != null) {
					deleteCommentsOfPost.close();
				}
				if (deletePosts != null) {
					deletePosts.close();
				}
				if (deleteUser != null) {
					deleteUser.close();
				}
				DBManager.getInstance().getConnection().setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
