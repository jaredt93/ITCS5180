package com.example.group20_inclass08;

public class Forum {
    String title, creator, creatorUid, description, docId;
    Object timeStamp;

    public Forum() {
        // empty constructor
    }

    public Forum(String title, String creator, String creatorUid, String description, Object timeStamp, String docId) {
        this.title = title;
        this.creator = creator;
        this.creatorUid = creatorUid;
        this.description = description;
        this.timeStamp = timeStamp;
        this.docId = docId;
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

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
