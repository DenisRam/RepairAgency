package com.training.model.entity;

import java.io.Serializable;

public class Service implements Serializable {
    private int id;
    private String serviceName;
    private double price;

    public Service() {
    }

    private Service(Builder builder){
        this.id = builder.id;
        this.serviceName = builder.serviceName;
        this.price = builder.price;
    }

    public int getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", serviceName='" + serviceName + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return id == service.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public static class Builder{
        private int id;
        private String serviceName;
        private double price;

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setServiceName(String serviceName){
            this.serviceName = serviceName;
            return this;
        }


        public Builder setPrice(double price){
            this.price = price;
            return this;
        }

        public Service build(){
            return new Service(this);
        }
    }
}
