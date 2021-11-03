package com.example.group20_hw05;

import com.google.firebase.Timestamp;

public class Forum {
    String title, creator, creatorUid, description, docId;
    Timestamp timeStamp;
    int likeCount;
    Boolean liked;

    public Forum() {
        // empty constructor
    }

    public Forum(String title, String creator, String creatorUid, String description, Timestamp timeStamp, String docId) {
        this.title = title;
        this.creator = creator;
        this.creatorUid = creatorUid;
        this.description = description;
        this.timeStamp = timeStamp;
        this.docId = docId;
        this.likeCount = 0;
        this.liked = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorUid() {
        return creatorUid;
    }

    public void setCreatorUid(String creatorUid) {
        this.creatorUid = creatorUid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }
}
