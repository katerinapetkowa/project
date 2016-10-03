package model;

import java.time.LocalDateTime;

public class Comment {

	private int commentId;
	private int userId;
	private int postId;
	// ?parrent comment
	private String text;
	private int points;
	private LocalDateTime uploadDate;

	public Comment(int commentId, int userId, int postId, String text, int points, LocalDateTime uploadDate) {
		this.commentId = commentId;
		this.userId = userId;
		this.postId = postId;
		this.text = text;
		this.points = points;
		this.uploadDate = uploadDate;
	}

	public int getCommentId() {
		return commentId;
	}

	public int getUserId() {
		return userId;
	}

	public int getPostId() {
		return postId;
	}

	public String getText() {
		return text;
	}

	public int getPoints() {
		return points;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

}
