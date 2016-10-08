package model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.db.CommentDAO;

public class CommentsManager {
	
	private static CommentsManager instance;
	private ConcurrentHashMap<Integer, HashMap<Integer, Comment>> commentsByPosts; //post id -> comment id -> comment 
	//nqkuv set ot users koito sa haresali i ne ?? ili vseki comment da si gi pazi
	
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
	
	public HashMap<Integer,Comment> getCommentsOfPost(int postId){
//		ConcurrentHashMap<Integer,Comment> commentsOfPost = new ConcurrentHashMap<>();
//		if(CommentsManager.getInstance().getCommentsByPosts().containsKey(postId)){
//			commentsOfPost.putAll(CommentsManager.getInstance().getCommentsByPosts().get(postId));
//		}
//		return commentsOfPost;
		System.out.println(commentsByPosts.get(postId).size());
		return commentsByPosts.get(postId);
	}
	
	public Comment getComment(int postId, int commentId){
		return commentsByPosts.get(postId).get(commentId);
	}
	
	public void uploadComment(String username, int postId, String text, int points, LocalDateTime uploadDate){
		int commentId = CommentDAO.getInstance().addCommentToDB(username, postId, text, points, uploadDate);
		Comment comment = new Comment(commentId, username, postId, text, points, uploadDate);
		System.out.println(comment.toString());
		if(!CommentsManager.getInstance().commentsByPosts.contains(postId)){
			CommentsManager.getInstance().commentsByPosts.put(postId, new HashMap<Integer, Comment>());
			System.out.println("adding post id to collection of comments by posts");
		}
		CommentsManager.getInstance().commentsByPosts.get(postId).put(commentId, comment);
		System.out.println("adding comment by id to collection by post ids");
		UsersManager.getInstance().getUser(username).addCommentToUser(postId, commentId);
	}
	
	public void deleteComment(String username, int postId, int commentId){
		UsersManager.getInstance().getUser(username).deleteCommentFromUser(postId, commentId);
		CommentsManager.getInstance().commentsByPosts.get(postId).remove(commentId);
		UsersManager.getInstance().getUser(username).deleteCommentFromUser(postId, commentId);
		CommentDAO.getInstance().deleteCommentFromDB(commentId);
	}
	
	public void deleteAllCommentsOfPost(int postId){
		
		CommentsManager.getInstance().commentsByPosts.remove(postId);
	}
	
}
