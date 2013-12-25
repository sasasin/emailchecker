package net.sasasin.emailchecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

public class DnsClient {

	public DnsClient() {
	}

	public List<Record> lookUpMx(String hostname) throws TextParseException {
		Record[] result = null;
		// まずはMX.
		result = new Lookup(hostname, Type.MX).run();
		// A でリトライ.
		if (result.length == 0) {
			result = new Lookup(hostname, Type.A).run();
		}
		// 一切みつからなければ空リストを返す.
		if (result.length == 0) {
			return new ArrayList<Record>();
		}
		return Arrays.asList(result);
	}

}
