package model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;



public class User {
	
	private String username;
	private String name;
	private String password;
	private String email;
	private String profilePicture;
	private ConcurrentHashMap<Integer,Post> posts; //post id -> post
	//collections of liked/commented posts ??
	//collection of comments of other user's posts
	
	//private ConcurrentHashMap<Integer,Post> likedPosts;
	//private ConcurrentHashMap<Integer,Post> commentedPosts;
	private ConcurrentHashMap<Integer, Set<Integer>> comments;
	
	
	public User(String username, String name, String password, String email, String profilePicture, ConcurrentHashMap<Integer, Post> posts) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.email = email;
		this.profilePicture = profilePicture;
		this.posts = new ConcurrentHashMap<>();
		this.posts.putAll(posts);
	}
	
	
	public Map<Integer, Post>  getFreshPosts(){
		Map<Integer, Post> freshPosts = new TreeMap<>(Collections.reverseOrder());
		for(Post p : this.getPosts().values()){
			freshPosts.put(p.getPostId(), p);
		}
		return freshPosts;
	}
	
	public Map<Integer, Post>  getHotPosts(){
		Map<Integer, Post> hotPosts = new TreeMap<>(Collections.reverseOrder());
		for(Post p : this.getPosts().values()){
			hotPosts.put(p.getPoints(), p);
		}
		return hotPosts;
	}
	
	public void addPost(Post post){
		this.posts.put(post.getPostId(), post);
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
	
	public String getProfilePicture() {
		return profilePicture;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	
	public Map<Integer, Post> getPosts() {
		return Collections.unmodifiableMap(posts);
	}
	
	public Map<Integer, Set<Integer>> getComments() {
		return Collections.unmodifiableMap(comments);
	}
	
	public void addCommentToUser(int postId, int commentId){
		if(!this.comments.containsKey(postId)){
			this.comments.put(postId, new HashSet<Integer>());
		}
		this.comments.get(postId).add(commentId);
	}
	
	public void deleteCommentFromUser(int postId, int commentId){
		this.comments.get(postId).remove(commentId);
	}
	
	public void getUpVoteOfPost(int postId){
		this.posts.get(postId).getUpVote();
	}
	
	public void getDownVoteOfPost(int postId){
		this.posts.get(postId).getDownVote();
	}
	
}
