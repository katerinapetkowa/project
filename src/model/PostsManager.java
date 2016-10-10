package model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import model.db.PostDAO;

public class PostsManager {

	private static PostsManager instance;
	private ConcurrentHashMap<Integer, Post> allPosts;
	private ConcurrentHashMap<String, HashMap<Integer, Post>> postsByCategories;
	private ConcurrentHashMap<Integer, HashSet<String>> postUpvotes;
	private ConcurrentHashMap<Integer, HashSet<String>> postDownvotes;

	private PostsManager() {
		allPosts = new ConcurrentHashMap<>();
		for (Post p : PostDAO.getInstance().getAllPostsFromDB()) {
			allPosts.put(p.getPostId(), p);
			System.out.println(p.getPostId() + "," + p.getUsername() + "," + p.getCategory() + "," + p.getTitle() + ","
					+ p.getPoints() + "," + p.getUploadDate() + "," + p.getPicture());
		}
		System.out.println("Posts loaded successfully in collection");
		postsByCategories = new ConcurrentHashMap<>();

		for (Post p : PostDAO.getInstance().getAllPostsFromDB()) {
			if (!postsByCategories.containsKey(p.getCategory())) {
				postsByCategories.put(p.getCategory(), new HashMap<Integer, Post>());
			}
			postsByCategories.get(p.getCategory()).put(p.getPostId(), new Post(p.getPostId(), p.getUsername(),
					p.getCategory(), p.getTitle(), p.getPoints(), p.getUploadDate(), p.getPicture()));

		}
		System.out.println("Posts by category loaded successfully in collection");

		postUpvotes = new ConcurrentHashMap<>();
		postUpvotes.putAll(PostDAO.getInstance().getPostsUpvotesFromDB());
		System.out.println("Post upvotes loaded successfully in collection");

		postDownvotes = new ConcurrentHashMap<>();
		postDownvotes.putAll(PostDAO.getInstance().getPostsDownvotesFromDB());
		System.out.println("Post downvotes loaded successfully in collection");

	}

	public synchronized static PostsManager getInstance() {
		if (instance == null) {
			instance = new PostsManager();
		}
		return instance;
	}

	public Post getPost(int postId) {
		return allPosts.get(postId);
	}

	public Map<Integer, Post> getFreshPosts() {
		Map<Integer, Post> freshPosts = new TreeMap<>(Collections.reverseOrder());
		freshPosts.putAll(PostsManager.getInstance().getAllPosts());
		return freshPosts;
	}

	public Map<Integer, Post> getHotPosts() {
		Map<Integer, Post> hotPosts = new TreeMap<>(Collections.reverseOrder());
		for (Post p : PostsManager.getInstance().getAllPosts().values()) {
			hotPosts.put(p.getPoints(), p);
		}
		return hotPosts;
	}

	public Map<Integer, Post> getFreshPostsByCategory(String category) {
		Map<Integer, Post> freshPostsByCategory = new TreeMap<>(Collections.reverseOrder());
		if (!PostsManager.getInstance().postsByCategories.containsKey(category)) {
			return freshPostsByCategory;
		}
		freshPostsByCategory.putAll(PostsManager.getInstance().getPostsByCategories().get(category));
		return freshPostsByCategory;
	}

	public Map<Integer, Post> getHotPostsByCategory(String category) {
		Map<Integer, Post> hotPostsByCategory = new TreeMap<>(Collections.reverseOrder());
		Map<Integer, Post> postByCategory = PostsManager.getInstance().getPostsByCategories().get(category);
		for (Post p : postByCategory.values()) {
			hotPostsByCategory.put(p.getPoints(), p);
		}
		return hotPostsByCategory;
	}

	public Map<Integer, Post> getAllPosts() {
		return Collections.unmodifiableMap(allPosts);
	}

	public Map<String, HashMap<Integer, Post>> getPostsByCategories() {
		return Collections.unmodifiableMap(postsByCategories);
	}

	public void uploadPost(String username, String category, String title, LocalDateTime uploadDate, String picture) {
		int postId = PostDAO.getInstance().addPostToDB(username, category, title, uploadDate, picture);
		Post post = new Post(postId, username, category, title, 0, uploadDate, picture);
		System.out.println(post.toString());
		PostsManager.getInstance().allPosts.put(post.getPostId(), post);
		if (!PostsManager.getInstance().postsByCategories.containsKey(category)) {
			PostsManager.getInstance().postsByCategories.put(category, new HashMap<Integer, Post>());
		}
		PostsManager.getInstance().postsByCategories.get(category).put(post.getPostId(), post);
		UsersManager.getInstance().getUser(username).addPost(post);
	}
	
	
	//TODO add upvoted posts into user's collection of liked posts

	public void upVotePost(String username, int postId) {
		if (!PostsManager.getInstance().postUpvotes.containsKey(postId)) {
			PostsManager.getInstance().postUpvotes.put(postId, new HashSet<String>());
		}
		PostsManager.getInstance().postUpvotes.get(postId).add(username);
		PostsManager.getInstance().allPosts.get(postId).getUpVote();
		String category = PostsManager.getInstance().getPost(postId).getCategory();
		PostsManager.getInstance().postsByCategories.get(category).get(postId).getUpVote();
		UsersManager.getInstance().getUser(username).getUpVoteOfPost(postId);
		PostDAO.getInstance().upvotePostInDB(username, postId);
	}

	public void downVotePost(String username, int postId) {
		if (!PostsManager.getInstance().postDownvotes.containsKey(postId)) {
			PostsManager.getInstance().postDownvotes.put(postId, new HashSet<String>());
		}
		PostsManager.getInstance().postDownvotes.get(postId).add(username);
		PostsManager.getInstance().allPosts.get(postId).getDownVote();
		String category = PostsManager.getInstance().getPost(postId).getCategory();
		PostsManager.getInstance().postsByCategories.get(category).get(postId).getDownVote();
		UsersManager.getInstance().getUser(username).getDownVoteOfPost(postId);
		PostDAO.getInstance().downvotePostInDB(username, postId);
	}

	public void reverseUpvote(String username, int postId) {
		PostsManager.getInstance().postUpvotes.get(postId).remove(username);
		PostsManager.getInstance().allPosts.get(postId).getDownVote();
		String category = PostsManager.getInstance().getPost(postId).getCategory();
		PostsManager.getInstance().postsByCategories.get(category).get(postId).getDownVote();
		UsersManager.getInstance().getUser(username).getDownVoteOfPost(postId);
		PostDAO.getInstance().reverseUpvoteInDB(username, postId);
	}

	public void reverseDownvote(String username, int postId) {
		PostsManager.getInstance().postDownvotes.get(postId).remove(username);
		PostsManager.getInstance().allPosts.get(postId).getUpVote();
		String category = PostsManager.getInstance().getPost(postId).getCategory();
		PostsManager.getInstance().postsByCategories.get(category).get(postId).getUpVote();
		UsersManager.getInstance().getUser(username).getUpVoteOfPost(postId);
		PostDAO.getInstance().reverseDownvoteInDB(username, postId);

	}

	public void upvoteToDownvote(String username, int postId) {
		PostsManager.getInstance().postUpvotes.get(postId).remove(username);
		PostsManager.getInstance().postDownvotes.get(postId).add(username);
		String category = PostsManager.getInstance().getPost(postId).getCategory();
		for (int i = 0; i < 2; i++) {
			PostsManager.getInstance().postsByCategories.get(category).get(postId).getDownVote();
			UsersManager.getInstance().getUser(username).getDownVoteOfPost(postId);
		}
		PostDAO.getInstance().upvoteToDownvteInDB(username, postId);

	}

	public void downvoteToUpvote(String username, int postId) {
		PostsManager.getInstance().postDownvotes.get(postId).remove(username);
		PostsManager.getInstance().postUpvotes.get(postId).add(username);
		String category = PostsManager.getInstance().getPost(postId).getCategory();
		for (int i = 0; i < 2; i++) {
			PostsManager.getInstance().postsByCategories.get(category).get(postId).getUpVote();
			UsersManager.getInstance().getUser(username).getUpVoteOfPost(postId);
		}
		PostDAO.getInstance().downvoteToUpvoteInDB(username, postId);
	}

	public Map<Integer, Post> searchPosts(String title) {
		Map<Integer, Post> posts = new HashMap<>();
		for (Post p : PostsManager.getInstance().getAllPosts().values()) {
			if (p.getTitle().toLowerCase().contains(title.toLowerCase())) {
				posts.put(p.getPostId(), p);
			}
		}
		return posts;
	}

	public void deletePost(int postId) {
		PostsManager.getInstance().allPosts.remove(postId);
		String postCategory = PostsManager.getInstance().getPost(postId).getCategory();
		PostsManager.getInstance().postsByCategories.get(postCategory).remove(postId);
		PostDAO.getInstance().deletePostFromDB(postId);
		for (Comment c : CommentsManager.getInstance().getCommentsOfPost(postId).values()) {
			UsersManager.getInstance().getUser(c.getUsername()).deleteCommentFromUser(postId, c.getCommentId());
		}
		CommentsManager.getInstance().deleteAllCommentsOfPost(postId);
	}

}
