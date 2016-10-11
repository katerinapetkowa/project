package model;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

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

	public boolean validUsername(String username) {
		if (registerredUsers.containsKey(username)) {
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
		UserDAO.getInstance().addUserToDB(username, name, password, email, profilePicture);
		User user = new User(username, name, password, email, profilePicture, "My Funny Collection", new ConcurrentHashMap<Integer, Post>(),
				new ConcurrentHashMap<Integer, Post>(), new ConcurrentHashMap<Integer, Post>(),
				new ConcurrentHashMap<Integer, Set<Integer>>(), new ConcurrentSkipListSet<Integer>());
		registerredUsers.put(username, user);
		System.out.println("User added successfully to collection of all users");
	}

//	public void changeName(String username, String name) {
//		UserDAO.getInstance().changeNameInDB(username, name);
//		User user = registerredUsers.get(username);
//		user.setName(name);
//		System.out.println("Name changed successfully in collection");
//	}

	public void changePassword(String username, String password) {
		UserDAO.getInstance().changePasswordInDB(username, password);
		User user = registerredUsers.get(username);
		user.setPassword(password);
		System.out.println("Password changed successfully in collection");
	}

//	public void changeEmail(String username, String email) {
//		UserDAO.getInstance().changeEmailInDB(username, email);
//		User user = registerredUsers.get(username);
//		user.setEmail(email);
//		;
//		System.out.println("Email changed successfully in collection");
//	}

	public void changeProfilePicture(String username, String profilePicture) {
		UserDAO.getInstance().changeProfilePictureInDB(username, profilePicture);
		User user = registerredUsers.get(username);
		user.setProfilePicture(profilePicture);
		System.out.println("Profile picture changed successfully in collection");
	}
	
	public void changeProfile(String username, String name, String email, String description){
		UserDAO.getInstance().changeProfileInDB(username, name, email, description);;
		User user = registerredUsers.get(username);
		user.setName(name);
		user.setEmail(email);
		user.setDescription(description);
		System.out.println("Profile changed successfully in collection");
	}

	public Map<String, User> getAllUsers() {
		return Collections.unmodifiableMap(registerredUsers);
	}

	public User getUser(String username) {
		return registerredUsers.get(username);
	}

	public String passwordToMD5(String password) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array;
			array = md.digest(password.getBytes("UTF-8"));
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void deleteUser(String username) {
		User user = UsersManager.getInstance().getUser(username);
		for(int postId : user.getComments().keySet()){  //deleting user's comments from other user's posts
			for(int commentId : user.getComments().get(postId)){
				CommentsManager.getInstance().removeCommentFromAllComments(postId, commentId);
			}
		}
		for(int postId : user.getPosts().keySet()){  //removing user's posts from collections, removing posts' comments and votes
			PostsManager.getInstance().removePostFromCollections(postId);
		}
		for(int postId : user.getUpvotedPosts().keySet()){ //removing upvotes from collection of upvotes
			PostsManager.getInstance().removeUpvoteFromCollection(postId, username);
		}
		for(int postId : user.getDownvotes()){ //removing downvotes from collection of downvotes
			PostsManager.getInstance().removeDownvoteFromCollection(postId, username);
		}
		UsersManager.getInstance().registerredUsers.remove(username); //removing user from collection of users
		UserDAO.getInstance().deleteUserFromDB(username); //removing user from db
	}

}
