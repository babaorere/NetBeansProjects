package com.webapp.elegircandidato;

/**
 *
 */
public class Registro {

    private static String nickname;
    private static String password;

    {
        nickname = null;
        password = null;
    }

    public static void login(String inNickname, String inPassword) {
        nickname = inNickname;
        password = inPassword;
    }

    public static void logout() {
        nickname = null;
        password = null;
    }

    public static String getNickname() {
        return nickname;
    }

}
