package com.ajjain.ci.analytics;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.Tuple;

/**
 * The Class CICounter.
 */
public class CICounter extends EvalFunc<Long> {

	/** The df. */
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:S");

	/**
	 * Interval.
	 *
	 * @param bag the bag
	 * @return the long
	 * @throws ParseException 
	 */
	private long interval(DataBag bag){
		Tuple first = null, last = null;
		for (Iterator<Tuple> itr = bag.iterator(); itr.hasNext();) {
			last = itr.next();
			if(first == null)
				first = last;
		}

		if(first == null || last == null){
			return -1;
		}
		else{
			try{
				String lastTS = (String) last.get(0);
				String firstTs = (String) first.get(0);

				Date lastDT = df.parse(lastTS);
				Date firstDt = df.parse(firstTs);

				return (lastDT.getTime() - firstDt.getTime())/1000;
			}
			catch(Exception e){
				throw new RuntimeException(e);
			}
		}

	}

	/**
	 * Refresh.
	 *
	 * @param bag the bag
	 * @return the long
	 */
	private long refresh(DataBag bag){
		long count = 0;
		Tuple prev = null;
		for (Iterator<Tuple> itr = bag.iterator(); itr.hasNext();) {
			Tuple curr = itr.next();
			if(prev != null){
				try{
					String prevUrl = (String) prev.get(1);
					String currUrl = (String) curr.get(1);
					if(prevUrl != null && currUrl != null && prevUrl.equals(currUrl))
						count++;
				} catch(Exception e){
					throw new RuntimeException(e);
				}
			}
			prev = curr;
		}
		return count;
	}

	/* (non-Javadoc)
	 * @see org.apache.pig.EvalFunc#exec(org.apache.pig.data.Tuple)
	 */
	@Override
	public Long exec(Tuple input) throws IOException {
		DataBag bag = (DataBag) input.get(0);
		String option = (String) input.get(1);
		switch (option) {
		case "INTERVAL":
			return interval(bag);

		case "REFRESH":
			return refresh(bag);

		default:
			throw new IllegalArgumentException("Illegal Option to CIEval option["+option+"]");
		}
	}

}
