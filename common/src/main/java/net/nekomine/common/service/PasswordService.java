package net.nekomine.common.service;

public interface PasswordService {

    String encryptPassword(String password);

    boolean verifyPassword(String inputPassword, String encryptedPassword);
}
