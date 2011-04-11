package com.bryanblack.parser;

public class TvaRelease extends Release {
	private String mObsDay, mObsHour, mUpstreamElev, mDownStreamElev, mAvgHourlyDischarge;
	
	/**
	 * @return the mObsDay
	 */
	public String getmObsDay() {
		return mObsDay;
	}

	/**
	 * @param mObsDay the mObsDay to set
	 */
	public void setmObsDay(String mObsDay) {
		this.mObsDay = mObsDay;
	}

	/**
	 * @return the mObsHour
	 */
	public String getmObsHour() {
		return mObsHour;
	}

	/**
	 * @param mObsHour the mObsHour to set
	 */
	public void setmObsHour(String mObsHour) {
		this.mObsHour = mObsHour;
	}

	/**
	 * @return the mUpstreamElev
	 */
	public String getmUpstreamElev() {
		return mUpstreamElev;
	}

	/**
	 * @param mUpstreamElev the mUpstreamElev to set
	 */
	public void setmUpstreamElev(String mUpstreamElev) {
		this.mUpstreamElev = mUpstreamElev;
	}

	/**
	 * @return the mDownStreamElev
	 */
	public String getmDownStreamElev() {
		return mDownStreamElev;
	}

	/**
	 * @param mDownStreamElev the mDownStreamElev to set
	 */
	public void setmDownStreamElev(String mDownStreamElev) {
		this.mDownStreamElev = mDownStreamElev;
	}

	/**
	 * @return the mAvgHourlyDischarge
	 */
	public String getmAvgHourlyDischarge() {
		return mAvgHourlyDischarge;
	}

	/**
	 * @param mAvgHourlyDischarge the mAvgHourlyDischarge to set
	 */
	public void setmAvgHourlyDischarge(String mAvgHourlyDischarge) {
		this.mAvgHourlyDischarge = mAvgHourlyDischarge;
	} 
}
