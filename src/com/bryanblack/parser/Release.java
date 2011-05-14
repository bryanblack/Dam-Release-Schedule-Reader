/**
 * 
 */
package com.bryanblack.parser;

import java.io.Serializable;


/**
 * @author Bryan
 *
 */
public abstract class Release implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String mReleaseDate, mTimePeriod, mGenerators;

	/**
	 * @return the releaseDate
	 */
	public String getReleaseDate() {
		return mReleaseDate;
	}

	/**
	 * @param releaseDate the releaseDate to set
	 */
	public void setReleaseDate(String releaseDate) {
		this.mReleaseDate = releaseDate;
	}

	/**
	 * @return the timePeriod
	 */
	public String getTimePeriod() {
		return mTimePeriod;
	}

	/**
	 * @param timePeriod the timePeriod to set
	 */
	public void setTimePeriod(String timePeriod) {
		this.mTimePeriod = timePeriod;
	}

	/**
	 * @return the generators
	 */
	public String getGenerators() {
		return mGenerators;
	}

	/**
	 * @param generators the generators to set
	 */
	public void setGenerators(String generators) {
		this.mGenerators = generators;
	} 
	
	
	
}
