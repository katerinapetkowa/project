package model;

import java.time.LocalDateTime;

public class Comment {

	private int commentId;
	private String username;
	private int postId;
	// ?parrent comment
	private String text;
	private int points;
	private LocalDateTime uploadDate;

	public Comment(int commentId, String username, int postId, String text, int points, LocalDateTime uploadDate) {
		this.commentId = commentId;
		this.username = username;
		this.postId = postId;
		this.text = text;
		this.points = points;
		this.uploadDate = uploadDate;
	}

	public int getCommentId() {
		return commentId;
	}

	public String getUsername() {
		return username;
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

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", username=" + username + ", postId=" + postId + ", text=" + text
				+ ", points=" + points + ", uploadDate=" + uploadDate + "]";
	}

}
