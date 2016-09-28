package model;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.db.UserDAO;

public class UsersManager {

	private static UsersManager instance;
	private ConcurrentHashMap<String, User> registerredUsers;

	private UsersManager() {
		registerredUsers = new ConcurrentHashMap<>();
		for (User u : UserDAO.getInstance().getAllUsersFromDB()) {
			registerredUsers.put(u.getUsername(), u);
		}
	}

	public synchronized static UsersManager getInstance() {
		if (instance == null) {
			instance = new UsersManager();
		}
		return instance;
	}
	
	public boolean validUsername(String username){
		if(registerredUsers.containsKey(username)){
			return false;
		}
		return true;
	}

	public boolean validLogin(String username, String password) {
		if (!registerredUsers.containsKey(username)) {
			return false;
		}
		return registerredUsers.get(username).getPassword().equals(password);
	}

	public void registerUser(String username, String name, String password, String email, String profilePicture) {
		int userId = UserDAO.getInstance().addUserToDB(username, name, password, email, profilePicture);
		User user = new User(userId, username, name, password, email, profilePicture);
		registerredUsers.put(username, user);
		System.out.println("User added successfully to collection of all users");
	}

	public void changeName(String username, String name) {
		UserDAO.getInstance().changeNameInDB(username, name);
		User user = registerredUsers.get(username);
		user.setName(name);
		System.out.println("Name changed successfully in collection");
	}

	public void changePassword(String username, String password) {
		UserDAO.getInstance().changePasswordInDB(username, password);
		User user = registerredUsers.get(username);
		user.setPassword(password);
		System.out.println("Password changed successfully in collection");
	}
	
	public void changeEmail(String username, String email) {
		UserDAO.getInstance().changeEmailInDB(username, email);
		User user = registerredUsers.get(username);
		user.setEmail(email);;
		System.out.println("Email changed successfully in collection");
	}
	
	public void changeProfilePicture(String username, String profilePicture) {
		UserDAO.getInstance().changeProfilePictureInDB(username, profilePicture);;
		User user = registerredUsers.get(username);
		user.setProfilePicture(profilePicture);;
		System.out.println("Profile picture changed successfully in collection");
	}

	public Map<String, User> getAllUsers() {
		return Collections.unmodifiableMap(registerredUsers);
	}

	public User getUser(String username) {
		return registerredUsers.get(username);
	}

}
