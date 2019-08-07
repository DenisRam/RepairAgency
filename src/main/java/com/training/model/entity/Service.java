package com.training.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Service implements Serializable {
    private int id;
    private String serviceNameUa;
    private String serviceNameEn;
    private BigDecimal price;

    public Service() {
    }

    private Service(Builder builder){
        this.id = builder.id;
        this.serviceNameUa = builder.serviceNameUa;
        this.serviceNameEn = builder.serviceNameEn;
        this.price = builder.price;
    }

    public int getId() {
        return id;
    }

    public String getServiceNameUa() {
        return serviceNameUa;
    }

    public String getServiceNameEn() {
        return serviceNameEn;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", serviceNameUa='" + serviceNameUa + '\'' +
                ", serviceNameEn='" + serviceNameEn + '\'' +
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
        private String serviceNameUa;
        private String serviceNameEn;
        private BigDecimal price;

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setServiceNameUa(String serviceNameUa){
            this.serviceNameUa = serviceNameUa;
            return this;
        }

        public Builder setServiceNameEn(String serviceNameEn){
            this.serviceNameEn = serviceNameEn;
            return this;
        }

        public Builder setPrice(BigDecimal price){
            this.price = price;
            return this;
        }

        public Service build(){
            return new Service(this);
        }
    }
}
