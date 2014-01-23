package org.be.kuleuven.hci.openbadges.testing;

import org.be.kuleuven.hci.openbadges.utils.SessionIdentifierGenerator;

public class TestingIdentifier {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionIdentifierGenerator sessid = new SessionIdentifierGenerator();
		System.out.println(sessid.nextSessionId());
	}

}
