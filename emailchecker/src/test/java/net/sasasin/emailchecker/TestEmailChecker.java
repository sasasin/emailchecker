package net.sasasin.emailchecker;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestEmailChecker {

	@Test
	public void testCheckAddress() {
		
		EMailChecker testee = new EMailChecker();
		
		assertTrue(testee.checkAddress("sasasin@sasasin.net"));
		assertFalse(testee.checkAddress("hoge@sasasin.net"));
		
		assertFalse(testee.checkAddress("aaaa@gmail.com"));

		assertTrue(testee.checkAddress("sasasin@gmail.com"));
		
	}

}
