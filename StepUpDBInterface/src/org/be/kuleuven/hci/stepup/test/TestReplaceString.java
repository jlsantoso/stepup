package org.be.kuleuven.hci.stepup.test;

import org.be.kuleuven.hci.stepup.controller.EventController;

public class TestReplaceString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String result = "\\\"";
		System.out.println(result);
		result = result.replaceAll("\\\\\"", "\"");
		System.out.println(result);
	}

}
