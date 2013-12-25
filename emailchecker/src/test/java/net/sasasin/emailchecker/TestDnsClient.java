package net.sasasin.emailchecker;

import java.util.List;

import org.junit.Test;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;

public class TestDnsClient {

	@Test
	public void testLookUpMx() {

		DnsClient testee = new DnsClient();

		String testdata = "gmail.com";

		List<Record> actual = null;
		try {
			actual = testee.lookUpMx(testdata);
		} catch (TextParseException e) {
			e.printStackTrace();
		}

		for (Record i : actual) {
			System.out.println(i.getAdditionalName());
		}

	}

}
