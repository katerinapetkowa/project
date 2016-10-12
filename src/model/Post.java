package model;

import java.time.LocalDateTime;

public class Post implements Comparable<Post> {

	private int postId;
	private String username;
	private String category; 
	private String title;
	private  int points;
	private LocalDateTime uploadDate;
	private String picture;
	
	
	public Post(int postId, String username, String category, String title, int points, LocalDateTime uploadDate, String picture) {
		this.postId = postId;
		this.username = username;
		this.category = category;
		this.title = title;
		this.points = points;
		this.uploadDate = uploadDate;
		this.picture = picture;
	}


	public int getPostId() {
		return postId;
	}


	public String getUsername() {
		return username;
	}


	public String getCategory() {
		return category;
	}


	public String getTitle() {
		return title;
	}


	public int getPoints() {
		return points;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

	public String getPicture() {
		return picture;
	}
	
	public void getUpVote(){
		this.points += 1;
	}

	public void getDownVote(){
		this.points -= 1;
	}

	@Override
	public int compareTo(Post p) {
		if(this.points == p.points){
			return -1;
		}
		return this.points - p.points;
	}


	@Override
	public String toString() {
		return "Post [postId=" + postId + ", username=" + username + ", category=" + category + ", title=" + title
				+ ", points=" + points + ", uploadDate=" + uploadDate + ", picture=" + picture + "]";
	}


}
