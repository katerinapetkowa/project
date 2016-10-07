package model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
					.executeQuery("SELECT username, name, password, email, profile_picture FROM users;");
			while (resultSet.next()) {
				ConcurrentHashMap<Integer, Post> posts = new ConcurrentHashMap<>();
				posts.putAll(PostDAO.getInstance().getPostsByUserFromDB(resultSet.getString("username")));
				users.add(new User(resultSet.getString("username"),
						resultSet.getString("name"), resultSet.getString("password"), resultSet.getString("email"),
						resultSet.getString("profile_picture"), posts));
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
					"INSERT INTO users (username, name, password, email, profile_picture) VALUES (?, ?, ?, ?, ?);");
			st.setString(1, username);
			st.setString(2, name);
			st.setString(3, password);
			st.setString(4, email);
			st.setString(5, profilePicture);
			st.executeUpdate();
//			ResultSet rs = st.getGeneratedKeys();
//			if (rs.next()) {
//				userId = rs.getInt(1);
//			}
//			rs.close();
			st.close();
			System.out.println("User added successfully to db");
		} catch (SQLException e) {
			System.out.println("Oops .. did not save the user in db");
			e.printStackTrace();
		}
	}

	public void changeNameInDB(String username, String name) {
		try {
			PreparedStatement ps = DBManager.getInstance().getConnection()
					.prepareStatement("UPDATE users SET name = ? WHERE username = ?;");
			ps.setString(1, name);
			ps.setString(2, username);
			// Statement st =
			// DBManager.getInstance().getConnection().createStatement();
			// st.executeUpdate("UPDATE users SET name = '" + name + "' WHERE
			// username = '" + username + "';");
			// st.close();
			System.out.println("Name changed successfully in db");
		} catch (SQLException e) {
			System.out.println("Oops .. did not change the name of the user");
			e.printStackTrace();
		}
	}

	public void changePasswordInDB(String username, String password) {
		try {

			Statement st = DBManager.getInstance().getConnection().createStatement();
			st.executeUpdate("UPDATE users SET password = '" + password + "' WHERE username = '" + username + "';");
			st.close();
			System.out.println("Password changed successfully in db");
		} catch (SQLException e) {
			System.out.println("Oops .. did not change the password of the user");
			e.printStackTrace();
		}
	}

	public void changeEmailInDB(String username, String email) {
		try {
			Statement st = DBManager.getInstance().getConnection().createStatement();
			st.executeUpdate("UPDATE users SET email = '" + email + "' WHERE username = '" + username + "';");
			st.close();
			System.out.println("Email changed succesfully in db");
		} catch (SQLException e) {
			System.out.println("Oops .. did not change the email of the user");
			e.printStackTrace();
		}
	}

	public void changeProfilePictureInDB(String username, String profilePicture) {
		try {
			Statement st = DBManager.getInstance().getConnection().createStatement();
			st.executeUpdate(
					"UPDATE users SET profile_picture = '" + profilePicture + "' WHERE username = '" + username + "';");
			st.close();
			System.out.println("Email changed succesfully in db");
		} catch (SQLException e) {
			System.out.println("Oops .. did not change the profile picture of the user");
			e.printStackTrace();
		}
	}

	public void deleteUserFromDB(String username) {
		PreparedStatement deleteCommentsOfUser = null;
		PreparedStatement deleteCommentsOfPost = null;
		PreparedStatement deletePosts = null;
		PreparedStatement deleteUser = null;
		try {
			DBManager.getInstance().getConnection().setAutoCommit(false);
			deleteCommentsOfUser = DBManager.getInstance().getConnection()
					.prepareStatement("DELETE FROM comments  WHERE username = ? ;");
			deleteCommentsOfUser.setString(1, username);
			deleteCommentsOfUser.executeUpdate();
			for (int i : UsersManager.getInstance().getUser(username).getPosts().keySet()) {
				deleteCommentsOfPost = DBManager.getInstance().getConnection()
						.prepareStatement("DELETE FROM comments  WHERE post_id = ? ;");
				deleteCommentsOfPost.setInt(1, i);
				deleteCommentsOfPost.executeUpdate();
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
