package com.training.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class Comment implements Serializable {
    private int id;
    private Integer orderId;
    private String commentText;

    public Comment() {
    }

    private Comment(Builder builder){
        this.id = builder.id;
        this.orderId = builder.orderId;
        this.commentText = builder.commentText;
    }

    public int getId() {
        return id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getCommentText() {
        return commentText;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", commentText='" + commentText + '\'' +
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
        private Integer orderId;
        private String commentText;

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setOrderId(Integer orderId){
            this.orderId = orderId;
            return this;
        }
        public Builder setCommentText(String commentText){
            this.commentText = commentText;
            return this;
        }

        public Comment build(){
            return new Comment(this);
        }
    }
}
