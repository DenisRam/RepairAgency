package com.training.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {

    private int id;
    private String login;
    private String password;

    public Account() {

    }

    private Account(Builder builder) {
        this.id = builder.id;
        this.login = builder.login;
        this.password = builder.password;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public static class Builder{
        private int id;
        private String login;
        private String password;

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setLogin(String login){
            this.login = login;
            return this;
        }

        public Builder setPassword(String password){
            this.password = password;
            return this;
        }

        public Account build(){
            return new Account(this);
        }
    }
}
