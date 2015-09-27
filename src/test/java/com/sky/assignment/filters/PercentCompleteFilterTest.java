package com.sky.assignment.filters;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

import com.sky.assignment.model.Recommendation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class PercentCompleteFilterTest {

	//The filter to be tested
	private RecFilter percentFilter;
	
	private static String BASE_DATE="2001-01-01 ";
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Before
	public void setup()
	{
		percentFilter = new PercentCompleteFilter();
	}
	
	
	/**
	 * Tests a number of possibilities
	 * Here we put them all in one method, but 
	 * ideally different test groups should be put in separate test methods for better code clarity
	 * 
	 * @throws Exception
	 */
    @Test
    public void testThatFilterPassesAll() throws Exception {
        
    	
    	//Happy scenarios
    	assertTrue("Recommend at 20% ", isRelevant("08:00:00","08:50:00","08:10:00","10:00:00"));
    	
    	assertTrue("Recommend at 59% ", isRelevant("08:00:00","09:40:00","08:59:00","10:00:00"));
    	assertTrue("Recommend at 60% ", isRelevant("08:00:00","09:40:00","09:00:00","10:00:00"));
    	
    	assertFalse("Discard above 60% ", isRelevant("08:00:00","09:00:00","08:37:00","10:00:00"));
    	
    	assertFalse("Discard at 61% ", isRelevant("08:00:00","09:40:00","09:01:00","10:00:00"));
    	assertFalse("Discard at 90% ", isRelevant("08:00:00","09:00:00","08:55:00","10:00:00"));
    	//Extreme cases
    	
    	assertFalse("Discard if in the past ", isRelevant("08:00:00","09:00:00","10:30:00","12:00:00"));
    	
    	assertTrue("Recommend in the future ", isRelevant("11:00:00","11:30:00","10:30:00","12:00:00"));
    	
    	
    }
    
    
    //A helper method to create and test a recommendation against a timeslot
    private boolean isRelevant(String recStartTime, String recEndTime, String slotStartTime, String slotEndTime) throws ParseException
    {
    	
    	//Generates a recommendation object with given start and end dates
    	Recommendation rec = new Recommendation("UUID", dateFormatter.parse(BASE_DATE + recStartTime).getTime(), dateFormatter.parse(BASE_DATE + recEndTime).getTime());
    	
    	return percentFilter.isRelevant(rec, dateFormatter.parse(BASE_DATE + slotStartTime).getTime(), dateFormatter.parse(BASE_DATE + slotEndTime).getTime());
    }
}
