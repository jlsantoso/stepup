package org.be.kuleuven.hci.openbadges.test;

import java.util.Calendar;

public class TestDate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar test = Calendar.getInstance();
		test.add(Calendar.YEAR, -1);
		System.out.println(test.getTime().toString());
		System.out.println(test.getTime().getTime());
	}

}
