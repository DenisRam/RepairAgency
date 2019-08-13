package com.training.model.entity;

import java.io.Serializable;

public class Comment implements Serializable {
    private int id;
    private int orderId;
    private String commentText;
    private int userId;

    public Comment() {
    }

    private Comment(Builder builder){
        this.id = builder.id;
        this.orderId = builder.orderId;
        this.commentText = builder.commentText;
        this.userId =builder.userId;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCommentText() {
        return commentText;
    }

    public int getUserId(){
        return userId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", commentText='" + commentText + '\'' +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public static class Builder{
        private int id;
        private int orderId;
        private String commentText;
        private int userId;

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setOrderId(int orderId){
            this.orderId = orderId;
            return this;
        }
        public Builder setCommentText(String commentText){
            this.commentText = commentText;
            return this;
        }
        public Builder setUserId(int userId){
            this.userId = userId;
            return this;
        }

        public Comment build(){
            return new Comment(this);
        }
    }
}
