package com.training.model.entity;

import javax.management.relation.Role;
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private int id;
    private String name;
    private String surname;
    private Integer accountId;
    private String role;

    public User() {

    }

    private User(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.surname = builder.surname;
        this.accountId = builder.accountId;
        this.role = builder.role;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", accountId=" + accountId +
                ", role='" + role + '\'' +
                '}';
    }

    public static class Builder{
        private int id;
        private String name;
        private String surname;
        private Integer accountId;
        private String role;

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Builder setSurname(String surname){
            this.surname = surname;
            return this;
        }

        public Builder setAccountId(Integer accountId){
            this.accountId = accountId;
            return this;
        }

        public Builder setRole(String role){
            this.role = role;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }
}
