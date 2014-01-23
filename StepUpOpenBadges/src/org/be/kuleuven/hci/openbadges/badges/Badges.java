package org.be.kuleuven.hci.openbadges.badges;

public class Badges {
	
	public static String firstBadge(String username){
		return "{\"recipient\":\""+username+"\",\"badge\":{\"version\":\"1.0\",\"name\":\"One tweet\",\"image\":\"/img/badge1.png\",\"description\":\"One tweet! - Do four tweets more and you will get the next Badge!\",\"criteria\":\"/badges/html5-basic\",\"issuer\":{\"origin\":\"http://openbadges-hci.appspot.com\",\"name\":\"hci-kuleuven\"}}}";
	}
	
	public static String secondBadge(String username){
		return "{\"recipient\":\""+username+"\",\"badge\":{\"version\":\"1.0\",\"name\":\"Five tweets\",\"image\":\"/img/badge5.png\",\"description\":\"Five tweet! - Do five tweets more and you will get the next Badge!\",\"criteria\":\"/badges/html5-basic\",\"issuer\":{\"origin\":\"http://openbadges-hci.appspot.com\",\"name\":\"hci-kuleuven\"}}}";
	}
	
	public static String thirdBadge(String username){
		return "{\"recipient\":\""+username+"\",\"badge\":{\"version\":\"1.0\",\"name\":\"Ten tweets\",\"image\":\"/img/badge10.png\",\"description\":\"Ten tweet! - Do five tweets more and you will get the next Badge\",\"criteria\":\"/badges/html5-basic\",\"issuer\":{\"origin\":\"http://openbadges-hci.appspot.com\",\"name\":\"hci-kuleuven\"}}}";
	}
	
	public static String fourthBadge(String username){
		return "{\"recipient\":\""+username+"\",\"badge\":{\"version\":\"1.0\",\"name\":\"Fifteen tweets\",\"image\":\"/img/badge15.png\",\"description\":\"Fifteen tweet! - Do five tweets more and you will get the next Badge\",\"criteria\":\"/badges/html5-basic\",\"issuer\":{\"origin\":\"http://openbadges-hci.appspot.com\",\"name\":\"hci-kuleuven\"}}}";
	}
	
	public static String fifthBadge(String username){
		return "{\"recipient\":\""+username+"\",\"badge\":{\"version\":\"1.0\",\"name\":\"Twenty tweets\",\"image\":\"/img/badge20.png\",\"description\":\"Done! You reached the maximum level! You are the king of the HCI beach!\",\"criteria\":\"/badges/html5-basic\",\"issuer\":{\"origin\":\"http://openbadges-hci.appspot.com\",\"name\":\"hci-kuleuven\"}}}";
	}
	
	public static String openBadgeFormat(String username, String version, String name, String description, String pathImg, String type, String connotation){
		return "{\"type\":\""+type+"\",\"connotation\":\""+connotation+"\",\"recipient\":\""+username+"\",\"badge\":{\"version\":\"1.0\",\"name\":\""+name+"-"+version+"\",\"image\":\""+pathImg+"\",\"description\":\""+description+"\",\"criteria\":\"/badges/html5-basic\",\"issuer\":{\"origin\":\"http://openbadges-hci.appspot.com\",\"name\":\"hci-kuleuven\"}}}";
	}
	
	public static String openBadgeFormat(String username, String version, String name, String description, String pathImg, String type, String connotation, String startdate, String enddate){
		return "{\"startdate\":\""+startdate+"\",\"enddate\":\""+enddate+"\",\"type\":\""+type+"\",\"connotation\":\""+connotation+"\",\"recipient\":\""+username+"\",\"badge\":{\"version\":\"1.0\",\"name\":\""+name+"-"+version+"\",\"image\":\""+pathImg+"\",\"description\":\""+description+"\",\"criteria\":\"/badges/html5-basic\",\"issuer\":{\"origin\":\"http://openbadges-hci.appspot.com\",\"name\":\"hci-kuleuven\"}}}";
	}
}

