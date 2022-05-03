package com.project.HotelDoor.data;

public class Comment {
    private String userUID;
    private String comment;

    //TODO:Maybe add functionality later
    private int countLikes;
    private int countDislikes;

    public Comment(String userUID, String comment, int countLikes, int countDislikes) {
        this.userUID = userUID;
        this.comment = comment;
        this.countLikes = countLikes;
        this.countDislikes = countDislikes;
    }

    public Comment(String userUID, String comment){
        this.userUID = userUID;
        this.comment = comment;
        this.countLikes = 0;
        this.countDislikes = 0;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCountLikes() {
        return countLikes;
    }

    public void setCountLikes(int countLikes) {
        this.countLikes = countLikes;
    }

    public int getCountDislikes() {
        return countDislikes;
    }

    public void setCountDislikes(int countDislikes) {
        this.countDislikes = countDislikes;
    }
}
