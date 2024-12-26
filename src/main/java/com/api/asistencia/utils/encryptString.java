/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author USUARIO
 */
public class encryptString 
{
    
    public static String SECRET_KEY="dgRpT4Yzfm?h?N4kSDErDq!CAcKKx6PbM8SyBY5R?TS3gi4XjNz954trRfBJXKkD7nxHQHY#SSH$cGb@";

    public static String encriptPassword(String pwd) 
    {
        return DigestUtils.sha256Hex(pwd);
    }
    
     public static String textDecryptor(String text)
    {
        String txtDecryptor="";
        try
        {
            String secretKey=SECRET_KEY;
            //
            byte[] message = Base64.decodeBase64(text.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            //
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            //
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher decipher = Cipher.getInstance("DESede");
            //
            decipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plainText = decipher.doFinal(message);
            txtDecryptor = new String(plainText, "UTF-8");
            //
            return txtDecryptor;
        }
        catch(UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex)
        {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    
    public static String textEncryptor(String text)
    {
        String txtecryptor="";
        try
        {
           String secretKey=SECRET_KEY;
           MessageDigest md = MessageDigest.getInstance("MD5");
           //
           byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
           byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
           //
           SecretKey key = new SecretKeySpec(keyBytes, "DESede");
           Cipher cipher = Cipher.getInstance("DESede");
           cipher.init(Cipher.ENCRYPT_MODE, key);
           //
            byte[] plainTextBytes = text.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            txtecryptor = new String(base64Bytes);
            return txtecryptor;
        }
        catch(UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex)
        {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    
    
}
