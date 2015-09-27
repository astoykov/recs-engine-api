package com.sky.assignment.filters;

import com.sky.assignment.model.Recommendation;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class PercentCompleteFilter implements RecFilter {

    @Override
    public boolean isRelevant(Recommendation r, long start, long end) {
       
        //An extra check to discard all recommendations from the past
        if (start > r.end) return false;
        
        
        if(start > r.start)
        {
        	double recDuration = r.end-r.start;
        	double passedFromStart = start-r.start;
        	if(passedFromStart/recDuration > 0.6) return false;
        }
        
    	// this filter should discard recommendations that running time is past 60%
        // for example if recommendation start time is 8:00 and end time is 9:00 then
        // this recommendation should be discarded if timeslot start is past 8:36, which is 60% of total show time
        return true;
    }
}
