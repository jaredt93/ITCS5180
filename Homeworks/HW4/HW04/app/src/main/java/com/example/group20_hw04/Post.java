package com.example.group20_hw04;

public class Post {
    String postText, postId, author, authorId, timeStamp;

    public Post() {
        // empty constructor
    }

    public Post(String postText, String postId, String author, String authorId, String timeStamp) {
        this.postText = postText;
        this.postId = postId;
        this.author = author;
        this.authorId = authorId;
        this.timeStamp = timeStamp;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
