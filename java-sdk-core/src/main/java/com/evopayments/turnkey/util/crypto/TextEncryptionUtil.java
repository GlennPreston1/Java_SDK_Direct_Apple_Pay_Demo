package com.evopayments.turnkey.util.crypto;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import com.evopayments.turnkey.apiclient.exception.TurnkeyInternalException;

/**
 * Based on https://mkyong.com/java/java-aes-encryption-and-decryption/
 */
public class TextEncryptionUtil {
	
	private static final String PROP_PASS_ENV_VAR = "TURNKEY-JAVA-SDK-PROPPASS";

	private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";

	private static final int TAG_LENGTH_BIT = 128; // must be one of {128, 120, 112, 104, 96}
	private static final int IV_LENGTH_BYTE = 12;
	private static final int SALT_LENGTH_BYTE = 16;
	
	private static final Charset UTF_8 = StandardCharsets.UTF_8;

	private TextEncryptionUtil() {
		//
	}

	/**
	 * @param pText
	 * @param password
	 * @return 
	 * 		base64 encoded AES encrypted text
	 * 
	 * @throws Exception
	 */
	public static String encrypt(String pText, String password) {

		try {

			// 16 bytes salt
			byte[] salt = CryptoUtils.getRandomNonce(SALT_LENGTH_BYTE);

			// GCM recommended 12 bytes iv
			byte[] iv = CryptoUtils.getRandomNonce(IV_LENGTH_BYTE);

			// secret key from password
			SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(password.toCharArray(), salt);

			Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

			// ASE-GCM needs GCMParameterSpec
			cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

			byte[] cipherText = cipher.doFinal(pText.getBytes(UTF_8));

			// prefix IV and salt to cipher text
			byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length).put(iv)
					.put(salt).put(cipherText).array();

			// string representation, base64, send this string to other for decryption
			return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);

		} catch (Exception e) {
			throw new TurnkeyInternalException("TextEncryptionUtil error!");
		}

	}
	
	public static String encryptBasedOnSystemPropPass(String cText) {
		
		final String propPass = System.getenv(PROP_PASS_ENV_VAR);
		return encrypt(cText, propPass);
		
	}

	/**
	 * @param cText    
	 * 		base64 encoded AES encrypted text
	 * @param password
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String cText, String password) {

		try {

			byte[] decode = Base64.getDecoder().decode(cText.getBytes(UTF_8));

			// get back the iv and salt from the cipher text
			ByteBuffer bb = ByteBuffer.wrap(decode);

			byte[] iv = new byte[IV_LENGTH_BYTE];
			bb.get(iv);

			byte[] salt = new byte[SALT_LENGTH_BYTE];
			bb.get(salt);

			byte[] cipherText = new byte[bb.remaining()];
			bb.get(cipherText);

			// get back the aes key from the same password and salt
			SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(password.toCharArray(), salt);

			Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

			cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

			byte[] plainText = cipher.doFinal(cipherText);

			return new String(plainText, UTF_8);

		} catch (Exception e) {
			throw new TurnkeyInternalException("TextEncryptionUtil error!");
		}

	}
	
	public static String decryptBasedOnSystemPropPass(String cText) {
		
		final String propPass = System.getenv(PROP_PASS_ENV_VAR);
		return decrypt(cText, propPass);
		
	}

}
