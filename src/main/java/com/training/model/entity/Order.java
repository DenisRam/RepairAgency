package com.training.model.entity;

import java.io.Serializable;
import java.sql.Date;


public class Order implements Serializable {
    private int id;
    private Integer userId;
    private Integer serviceId;
    private String status;
    private Integer managerId;
    private Date reviewDate;
    private String rejectionReason;
    private Integer masterId;
    private Date repairStartTime;
    private Date repairFinish;

    public Order() {
    }

    private Order(Builder builder){
        this.id = builder.id;
        this.userId = builder.userId;
        this.serviceId = builder.serviceId;
        this.status = builder.status;
        this.managerId = builder.managerId;
        this.reviewDate = builder.reviewDate;
        this.rejectionReason = builder.rejectionReason;
        this.masterId = builder.masterId;
        this.repairStartTime = builder.repairStartTime;
        this.repairFinish = builder.repairFinish;
    }

    public int getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public String getStatus() {
        return status;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public Date getRepairStartTime() {
        return repairStartTime;
    }

    public Date getRepairFinish() {
        return repairFinish;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", serviceId=" + serviceId +
                ", status='" + status + '\'' +
                ", managerId=" + managerId +
                ", reviewDate=" + reviewDate +
                ", rejectionReason='" + rejectionReason + '\'' +
                ", masterId=" + masterId +
                ", repairStartTime=" + repairStartTime +
                ", repairFinish=" + repairFinish +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public static class Builder{
        private int id;
        private Integer userId;
        private Integer serviceId;
        private String status;
        private Integer managerId;
        private Date reviewDate;
        private String rejectionReason;
        private Integer masterId;
        private Date repairStartTime;
        private Date repairFinish;

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setUserId(Integer userId){
            this.userId = userId;
            return this;
        }

        public Builder setServiceId(Integer serviceId){
            this.serviceId = serviceId;
            return this;
        }

        public Builder setStatus(String status){
            this.status = status;
            return this;
        }

        public Builder setManagerId(Integer managerId){
            this.managerId = managerId;
            return this;
        }

        public Builder setReviewDate(Date reviewDate){
            this.reviewDate = reviewDate;
            return this;
        }

        public Builder setRejectionReason(String rejectionReason){
            this.rejectionReason = rejectionReason;
            return this;
        }

        public Builder setMasterId(Integer masterId){
            this.masterId = masterId;
            return this;
        }

        public Builder setRepairStartTime(Date repairStartTime){
            this.repairStartTime = repairStartTime;
            return this;
        }

        public Builder setRepairFinish(Date repairFinish){
            this.repairFinish = repairFinish;
            return this;
        }

        public Order build(){
            return new Order(this);
        }
    }
}

