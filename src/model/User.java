package model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class User {

	private String username;
	private String name;
	private String password;
	private String email;
	private String profilePicture;
	private String description;

	private ConcurrentHashMap<Integer, Post> posts; // post id -> post

	private ConcurrentHashMap<Integer, Post> upvotedPosts;
	private ConcurrentHashMap<Integer, Post> commentedPosts;
	private ConcurrentHashMap<Integer, Set<Integer>> comments;
	private ConcurrentSkipListSet<Integer> downvotes;

	public User(String username, String name, String password, String email, String profilePicture, String description,
			ConcurrentHashMap<Integer, Post> posts, ConcurrentHashMap<Integer, Post> upvotedPosts,
			ConcurrentHashMap<Integer, Post> commentedPosts, ConcurrentHashMap<Integer, Set<Integer>> comments,
			ConcurrentSkipListSet<Integer> downvotes) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.email = email;
		this.profilePicture = profilePicture;
		this.description = description;
		this.posts = new ConcurrentHashMap<>();
		this.posts.putAll(posts);
		this.upvotedPosts = new ConcurrentHashMap<>();
		this.upvotedPosts.putAll(posts);
		this.commentedPosts = new ConcurrentHashMap<>();
		this.commentedPosts.putAll(posts);
		this.comments = new ConcurrentHashMap<>();
		this.comments.putAll(comments);
		this.downvotes = new ConcurrentSkipListSet<>();
		this.downvotes.addAll(downvotes);
	}

	public Map<Integer, Post> getFreshPosts() {
		Map<Integer, Post> freshPosts = new TreeMap<>(Collections.reverseOrder());
		for (Post p : this.getPosts().values()) {
			freshPosts.put(p.getPostId(), p);
		}
		return freshPosts;
	}

	public Map<Integer, Post> getHotPosts() {
		Map<Integer, Post> hotPosts = new TreeMap<>(Collections.reverseOrder());
		for (Post p : this.getPosts().values()) {
			hotPosts.put(p.getPoints(), p);
		}
		return hotPosts;
	}

	public void addPost(Post post) {
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

	public String getDescription() {
		return description;
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

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<Integer, Post> getPosts() {
		return Collections.unmodifiableMap(posts);
	}

	public Map<Integer, Set<Integer>> getComments() {
		return Collections.unmodifiableMap(comments);
	}

	public Map<Integer, Post> getUpvotedPosts() {
		return Collections.unmodifiableMap(upvotedPosts);
	}

	public Map<Integer, Post> getCommentedPosts() {
		return Collections.unmodifiableMap(commentedPosts);
	}

	public Set<Integer> getDownvotes() {
		return Collections.unmodifiableSet(downvotes);
	}

	public void addCommentToUser(int postId, int commentId, Post post) {
		this.commentedPosts.put(postId, post);
		if (!post.getUsername().equals(this.getUsername())) {
			if (!this.comments.containsKey(postId)) {
				this.comments.put(postId, new TreeSet<Integer>());
			}
			this.comments.get(postId).add(commentId);
		}
	}

	public void deleteCommentFromUser(int postId, int commentId) {
		if (this.comments.containsKey(postId)) {
			this.comments.get(postId).remove(commentId);
		}
	}

	public void getUpVoteOfPost(int postId) {
		if (this.posts.containsKey(postId)) {
			this.posts.get(postId).getUpVote();
		}
	}

	public void getDownVoteOfPost(int postId) {
		if (this.posts.containsKey(postId)) {
			this.posts.get(postId).getDownVote();
		}
	}

	public void upvotePost(int postId, Post post) {
		this.upvotedPosts.put(postId, post);
	}

	public void removeUpvoteOfPost(int postId) {
		this.upvotedPosts.remove(postId);
	}

	public void downvotePost(int postId) {
		this.downvotes.add(postId);
	}

	public void removeDownvoteOfPost(int postId) {
		this.downvotes.remove(postId);
	}

	public void removeCommentedPost(int postId) {
		this.commentedPosts.remove(postId);
	}

}
