package com.example.zenitka.teamcommander.helpers;

public class Errors {
    public static final byte CODE_OK = 0;
    public static final byte CODE_NOT_FOUND = 1;
    public static final byte CODE_WRONG_PASSWORD = 2;
    public static final byte CODE_INCORRECT_REQUEST = 3;
    public static final byte CODE_JSON_ERROR = 4;
    public static final byte CODE_SQL_ERROR = 5;
    public static final byte CODE_USER_ALREADY_EXISTS = 6;

    public static final String MESSAGE_OK = "OK";
    public static final String MESSAGE_NOT_FOUND = "Нет пользователя с данным логином";
    public static final String MESSAGE_WRONG_PASSWORD = "Неверный пароль";
    public static final String MESSAGE_INCORRECT_REQUEST = "некорректный запрос";
    public static final String MESSAGE_JSON_ERROR = "ошибка в JSON";
    public static final String MESSAGE_SQL_ERROR = "ошибка в SQL-запросе к серверу";
    public static final String MESSAGE_USER_ALREADY_EXISTS = "такой юзер уже существует";

    public static final String[] ERRORS = {
            MESSAGE_OK,
            MESSAGE_NOT_FOUND,
            MESSAGE_WRONG_PASSWORD,
            MESSAGE_INCORRECT_REQUEST,
            MESSAGE_JSON_ERROR,
            MESSAGE_SQL_ERROR,
            MESSAGE_USER_ALREADY_EXISTS};
}
