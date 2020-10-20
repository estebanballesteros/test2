package com.octagon.gestionclientes.security.utils;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class CipherUtility {

    private static KeyPairGenerator keyPairGenerator = null;

    private final SecureRandom random = new SecureRandom();

    public static final String RSA = "RSA";

    public static final String SHA_256 = "SHA-256";

    public KeyPair getKeyPair() {
        return keyPairGenerator.genKeyPair();
    }

    // encrypts a plain text with a RSA key
    public String encrypt(String content, Key pubKey) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] contentBytes = content.getBytes();
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] cipherContent = cipher.doFinal(contentBytes);
        String encoded = Base64.getEncoder().encodeToString(cipherContent);
        return encoded;
    }

    public String decrypt(String cipherContent, Key privKey) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, privKey);
        byte[] cipherContentBytes = Base64.getDecoder().decode(cipherContent.getBytes());
        byte[] decryptedContent = cipher.doFinal(cipherContentBytes);
        String decoded = new String(decryptedContent);
        return decoded;
    }

    public String encodeKey(Key key) {
        byte[] keyBytes = key.getEncoded();
        String encodedKeyStr = Base64.getEncoder().encodeToString(keyBytes);
        return encodedKeyStr;
    }

    public PublicKey decodePublicKey(String keyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.getDecoder().decode(keyStr);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey key = keyFactory.generatePublic(spec);
        return key;
    }

    public PrivateKey decodePrivateKey(String keyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.getDecoder().decode(keyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        return key;
    }

    public String hashString(String originalString) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
        byte[] encodedhash = messageDigest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        String encodedhashStr = Base64.getEncoder().encodeToString(encodedhash);
        return encodedhashStr;
    }

    public Boolean evaluateSign(String content, String signature, Key pubKey) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String decryptedContent = this.decrypt(signature, pubKey);
        String hashContent = this.hashString(content);
        return hashContent.equals(decryptedContent);
    }

    @PostConstruct
    private void init() {
        try {
            if (keyPairGenerator == null) keyPairGenerator = KeyPairGenerator.getInstance(RSA);
            keyPairGenerator.initialize(1024, random);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
