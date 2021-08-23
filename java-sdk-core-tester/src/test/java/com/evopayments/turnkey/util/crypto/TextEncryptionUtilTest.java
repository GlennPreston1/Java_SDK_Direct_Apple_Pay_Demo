package com.evopayments.turnkey.util.crypto;

import org.junit.Assert;
import org.junit.Test;


public class TextEncryptionUtilTest {
	
	@Test
	public void test() {

		String str = "56789";
		String encPass = "test";
		String encryptedStr = TextEncryptionUtil.encrypt(str, encPass);
		String decryptedStr = TextEncryptionUtil.decrypt(encryptedStr, encPass);

		Assert.assertNotEquals(str, encryptedStr);
		Assert.assertEquals(str, decryptedStr);
	}

}
