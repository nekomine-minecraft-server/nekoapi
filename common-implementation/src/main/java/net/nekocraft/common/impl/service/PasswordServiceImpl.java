package net.nekocraft.common.impl.service;

import net.nekomine.common.service.PasswordService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("unused")
public class PasswordServiceImpl implements PasswordService {

    public String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();

            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean verifyPassword(String inputPassword, String encryptedPassword) {
        String encryptedInputPassword = encryptPassword(inputPassword);
        return encryptedInputPassword != null && encryptedInputPassword.equals(encryptedPassword);
    }

}
