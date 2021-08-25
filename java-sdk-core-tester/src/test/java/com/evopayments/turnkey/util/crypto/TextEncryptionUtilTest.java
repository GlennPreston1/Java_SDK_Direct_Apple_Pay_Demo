package com.evopayments.turnkey.util.crypto;

import org.junit.Assert;
import org.junit.Test;


public class TextEncryptionUtilTest {
	
	@Test
	public void test() {

		String str = "x";
		String ep = "y";
		String encryptedStr = TextEncryptionUtil.encrypt(str, ep);
		String decryptedStr = TextEncryptionUtil.decrypt(encryptedStr, ep);

		Assert.assertNotEquals(str, encryptedStr);
		Assert.assertEquals(str, decryptedStr);
	}

}
