package org.be.kuleuven.hci.stepup.test;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.be.kuleuven.hci.stepup.model.ActivityStream;
import org.be.kuleuven.hci.stepup.persistancelayer.RestClient;
import org.be.kuleuven.hci.stepup.util.StepUpConstants;

public class TestGonzaloService {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		ActivityStream as = new ActivityStream();
		as.setActor("erikduval");
		as.setVerb("commented");
		as.setPublishedDate(Calendar.getInstance().getTime());
		as.setObject("http://teamtgv.wordpress.com/2013/02/19/choo-choo-motherfucker-jpg/comment-page-1/#comment-2","Where you folks the group who wondered how informal the communication on the student blogs should be ;-) ?");
		RestClient.doPost("http://chi13course.appspot.com/api/activities/add", as.getActivityStream().toString());
	}

}

//{"verb":"commented","object":{"id":"generated","displayName":"Where you folks the group who wondered how informal the communication on the student blogs should be ;-) ?","url":"http://teamtgv.wordpress.com/2013/02/19/choo-choo-motherfucker-jpg/comment-page-1/#comment-2"},"actor":{"id":"erikduval","displayName":"erikduval","objectType":"person"},"published":"2013-02-19T21:32:22.000+0100"}
