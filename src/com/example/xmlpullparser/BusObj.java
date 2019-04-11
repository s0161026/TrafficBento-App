package com.example.xmlpullparser;

public class BusObj {

	public BusObj() {

	}

	public BusObj(String goBack, String cTime, String ivrno) {
		super();
		
		GoBack = goBack;
		comeTime = cTime;
		IVRNO = ivrno;
	}

	private String GoBack, comeTime, IVRNO;


	public String getGoBack() {
		return GoBack;
	}

	public void setGoBack(String goBack) {
		GoBack = goBack;
	}

	public String getcomeTime() {
		return comeTime;
	}

	public void setcomeTime(String cTime) {
		comeTime = cTime;
	}

	public String getIVRNO() {
		return IVRNO;
	}

	public void setIVRNO(String ivrno) {
		IVRNO = ivrno;
	}


}
