package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import model.Comment;


public class CommentDAO {

	private static CommentDAO instance;
//	private ConcurrentHashMap<Integer, HashMap<Integer, Comment>> commentsByPosts;
//	
//	CommentDAO(){
//		commentsByPosts = new ConcurrentHashMap<>();
//		for (Comment c : getAllCommentsFromDB()) {
//			if(!commentsByPosts.containsKey(c.getPostId())){
//				commentsByPosts.put(c.getPostId(), new HashMap<Integer, Comment>());
//			}
//			commentsByPosts.get(c.getPostId()).put(c.getCommentId(), c);
//		}
//	}
	
	public synchronized static CommentDAO getInstance(){
		if(instance == null){
			instance = new CommentDAO();
		}
		return instance;
	}
	
	
	public Set<Comment> getAllCommentsFromDB(){
		Set<Comment> comments = new HashSet<Comment>();
		try {
			Statement st = DBManager.getInstance().getConnection().createStatement();
			String query = "SELECT comment_id, user_id, post_id, text, points, upload_date FROM comments;";
			ResultSet resultSet = st.executeQuery(query);
			while (resultSet.next()) {
				comments.add(new Comment(resultSet.getInt("comment_id"), resultSet.getInt("user_id"),
						resultSet.getInt("post_id"), resultSet.getString("text"), resultSet.getInt("points"),
						resultSet.getTimestamp("upload_date").toLocalDateTime()));
			}
			resultSet.close();
			st.close();
			
		} catch (SQLException e) {
			System.out.println("Oops, select comments from db.");
		}
		System.out.println("Comments from db selected successfully");
		return comments;
	}
	
	
//	public ConcurrentHashMap<Integer,Comment> getCommentsOfPostFromDB(int postId){
//		ConcurrentHashMap<Integer,Comment> commentsOfPost = new ConcurrentHashMap<>();
//		try {
//			Statement st = DBManager.getInstance().getConnection().createStatement();
//			String query = "SELECT comment_id, user_id, post_id, text, points, upload_date FROM comments WHERE post_id = " + postId + ";";
//			ResultSet resultSet = st.executeQuery(query);
//			while (resultSet.next()) {
//				commentsOfPost.put(resultSet.getInt("comment_id"), new Comment(resultSet.getInt("comment_id"), resultSet.getInt("user_id"),
//						resultSet.getInt("post_id"), resultSet.getString("text"), resultSet.getInt("points"),
//						resultSet.getTimestamp("upload_date").toLocalDateTime()));
//			}
//			resultSet.close();
//			st.close();
//			
//		} catch (SQLException e) {
//			System.out.println("Oops, select comments of post from db.");
//		}
//		System.out.println("Comments of post from db selected successfully");
//		return commentsOfPost;
//	}
	
	
	public void addCommentToDB(){
		//TODO
	}
	
	//TODO add comment, delete comment 
	
	
}
