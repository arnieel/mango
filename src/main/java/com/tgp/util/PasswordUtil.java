package com.tgp.util;

import org.jasypt.util.password.StrongPasswordEncryptor;

public final class PasswordUtil {

    private static StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();

    public static boolean checkPassword(String password) {
        return encryptor.checkPassword(password, PropertiesUtil.getValue("app.admin.password"));
    }
}
