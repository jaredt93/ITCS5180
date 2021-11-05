package com.example.group20_hw05;

import com.google.firebase.Timestamp;

public class Comment {
    String creator, creatorUid, description, docId;
    Timestamp timeStamp;

    public Comment() {
    }

    public Comment(String creator, String creatorUid, String description, String docId, Timestamp timeStamp) {
        this.creator = creator;
        this.creatorUid = creatorUid;
        this.description = description;
        this.docId = docId;
        this.timeStamp = timeStamp;
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

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }
}
