package com.example.group20_hw05;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.HashMap;

public class Forum implements Serializable {
    String title, creator, creatorUid, description, docId;
    Timestamp timeStamp;
    HashMap<String, Object> likedBy;

    public Forum() {
        // empty constructor
    }

    public Forum(String title, String creator, String creatorUid, String description, Timestamp timeStamp, String docId, HashMap<String, Object> likedBy) {
        this.title = title;
        this.creator = creator;
        this.creatorUid = creatorUid;
        this.description = description;
        this.timeStamp = timeStamp;
        this.docId = docId;
        this.likedBy = likedBy;
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

    public HashMap<String, Object> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(HashMap<String, Object>  likedBy) {
        this.likedBy = likedBy;
    }
}
