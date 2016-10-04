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
		if(CommentsManager.getInstance().getCommentsByPosts().containsKey(postId)){
			commentsOfPost.putAll(CommentsManager.getInstance().getCommentsByPosts().get(postId));
		}
		return commentsOfPost;
	}
	
	
	public void uploadComment(String username, int postId, String text, int points, LocalDateTime uploadDate){
		int commentId = CommentDAO.getInstance().addCommentToDB(username, postId, text, points, uploadDate);
		int userId = UsersManager.getInstance().getUser(username).getUserId();
		Comment comment = new Comment(commentId, userId, postId, text, points, uploadDate);
		System.out.println(comment.toString());
		if(!CommentsManager.getInstance().commentsByPosts.contains(postId)){
			CommentsManager.getInstance().commentsByPosts.put(postId, new HashMap<Integer, Comment>());
		}
		CommentsManager.getInstance().commentsByPosts.get(postId).put(commentId, comment);
	}
	
	public void deleteComment(int postId, int commentId){
		CommentsManager.getInstance().commentsByPosts.get(postId).remove(commentId);
		CommentDAO.getInstance().deleteCommentFromDB(commentId);
	}
	
}
