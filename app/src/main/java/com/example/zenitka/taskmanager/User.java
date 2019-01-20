package com.example.zenitka.taskmanager;

public class User {
    private String name, surname, login, pass_hash;

    public User(String name, String surname, String login, String pass_hash) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.pass_hash = pass_hash;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLogin() {
        return login;
    }

    public String getPass_hash() {
        return pass_hash;
    }

    public String toString() {
        return "User named " + name + " " + surname + " with login " + login;
    }
}
