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
					.executeQuery("SELECT user_id, username, name, password, email, profile_picture FROM users;");
			while (resultSet.next()) {
				ConcurrentHashMap<Integer, Post> posts = new ConcurrentHashMap<>();
				posts.putAll(PostDAO.getInstance().getPostsByUserFromDB(resultSet.getInt("user_id")));
				users.add(new User(resultSet.getInt("user_id"), resultSet.getString("username"),
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

	
	public int addUserToDB(String username, String name, String password, String email, String profilePicture) {
		int userId = 0;
		try {
			PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(
					"INSERT INTO users (username, name, password, email, profile_picture) VALUES (?, ?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, username);
			st.setString(2, name);
			st.setString(3, password);
			st.setString(4, email);
			st.setString(5, profilePicture);
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
				userId = rs.getInt(1);
			}
			rs.close();
			st.close();
			System.out.println("User added successfully to db");
		} catch (SQLException e) {
			System.out.println("Oops .. did not save the user in db");
			e.printStackTrace();
		}
		return userId;
	}
	
	
	public void changeNameInDB(String username, String name){
		try {
			Statement st = DBManager.getInstance().getConnection().createStatement();
			st.executeUpdate("UPDATE users SET name = '" + name +  "' WHERE username = '" + username + "';");
			st.close();
			System.out.println("Name changed successfully in db");
		} catch (SQLException e) {
			System.out.println("Oops .. did not change the name of the user");
			e.printStackTrace();
		}
	}
	
	public void changePasswordInDB(String username, String password){
		try {
			
			Statement st = DBManager.getInstance().getConnection().createStatement();
			st.executeUpdate("UPDATE users SET password = '" + password +  "' WHERE username = '" + username + "';");
			st.close();
			System.out.println("Password changed successfully in db");
		} catch (SQLException e) {
			System.out.println("Oops .. did not change the password of the user");
			e.printStackTrace();
		}
	}
	
	
	public void changeEmailInDB(String username, String email){
		try {
			Statement st = DBManager.getInstance().getConnection().createStatement();
			st.executeUpdate("UPDATE users SET email = '" + email +  "' WHERE username = '" + username + "';");
			st.close();
			System.out.println("Email changed succesfully in db");
		} catch (SQLException e) {
			System.out.println("Oops .. did not change the email of the user");
			e.printStackTrace();
		}
	}
	
	
	public void changeProfilePictureInDB(String username, String profilePicture){
		try {
			Statement st = DBManager.getInstance().getConnection().createStatement();
			st.executeUpdate("UPDATE users SET profile_picture = '" + profilePicture +  "' WHERE username = '"	+ username + "';");
			st.close();
			System.out.println("Email changed succesfully in db");
		} catch (SQLException e) {
			System.out.println("Oops .. did not change the profile picture of the user");
			e.printStackTrace();
		}
	}
	
	public void deleteUserFromDB(String username){
		try {
			PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(
					"DELETE FROM users  WHERE username = ? ;");
			st.setString(1, username);
			st.executeUpdate();
			st.close();
			System.out.println("User deleted successfully from db");
		} catch (SQLException e) {
			System.out.println("Oops .. did not delete the user from db");
			e.printStackTrace();
		}
	}
	
}
