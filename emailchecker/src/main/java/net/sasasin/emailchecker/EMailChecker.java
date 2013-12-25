package net.sasasin.emailchecker;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import org.apache.commons.net.smtp.SMTPClient;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;

public class EMailChecker {

	public EMailChecker() {

	}

	public boolean checkAddress(String emailAddress) {

		String[] parts = emailAddress.split("@");
		// @の前と、@の後の２つより多くても少なくてもNG。
		if (parts.length != 2) {
			return false;
		}

		String hostname = parts[1];

		DnsClient dnsClient = new DnsClient();
		List<Record> mailServers = null;
		try {
			mailServers = dnsClient.lookUpMx(hostname);
		} catch (TextParseException e) {
			e.printStackTrace();
			return false;
		}
		if (mailServers.size() == 0) {
			return false;
		}

		// MXの全サーバーに順に問い合わせる
		for (Record i : mailServers) {
			SMTPClient client = new SMTPClient();
			client.setConnectTimeout(2000);
			try {
				String mailServer = i.getAdditionalName().toString();

				client.connect(mailServer);
				client.setSender(emailAddress);

				return client.addRecipient(emailAddress);

			} catch (SocketException e) {
				System.err.println("connect to: "
						+ i.getAdditionalName().toString());
				System.err.println("check for: " + emailAddress);
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("connect to: "
						+ i.getAdditionalName().toString());
				System.err.println("check for: " + emailAddress);
				e.printStackTrace();
			} finally {
				try {
					client.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// 事ここに至っても判明しなければ、もはや諦めるしかない
		return false;
	}
}
