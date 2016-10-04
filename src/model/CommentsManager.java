package model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.db.CommentDAO;

public class CommentsManager {
	
	private static CommentsManager instance;
	private ConcurrentHashMap<Integer, HashMap<Integer, Comment>> commentsByPosts;
	
	public CommentsManager() {
		commentsByPosts = new ConcurrentHashMap<>();
		for (Comment c : CommentDAO.getInstance().getAllCommentsFromDB()) {
			if(!commentsByPosts.containsKey(c.getPostId())){
				commentsByPosts.put(c.getPostId(), new HashMap<Integer, Comment>());
			}
			commentsByPosts.get(c.getPostId()).put(c.getCommentId(), c);
		}
	}
	
	public synchronized static CommentsManager getInstance(){
		if(instance == null){
			instance = new CommentsManager();
		}
		return instance;
	}
	
	public Map<Integer, HashMap<Integer, Comment>> getCommentsByPosts() {
		return Collections.unmodifiableMap(commentsByPosts);
	}
	
	public ConcurrentHashMap<Integer,Comment> getCommentsOfPost(int postId){
		ConcurrentHashMap<Integer,Comment> commentsOfPost = new ConcurrentHashMap<>();
		commentsOfPost.putAll(CommentsManager.getInstance().getCommentsByPosts().get(postId));
		return commentsOfPost;
	}
	
	
	public void uploadComment(int commentId, int userId, int postId, String text, int points, LocalDateTime uploadDate){
		//TODO
	}
	
}
