package za.ac.unisa.lms.tools.studentstatus.forms;

import java.util.ArrayList;

public class ScreeningVenue {	
	
	private String venueCode;
	private String venueName;
	private ArrayList<String> addressList = new ArrayList<String>();
	
	public String getVenueCode() {
		return venueCode;
	}
	public void setVenueCode(String venueCode) {
		this.venueCode = venueCode;
	}
	public String getVenueName() {
		return venueName;
	}
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	public ArrayList<String> getAddressList() {
		return addressList;
	}
	public void setAddressList(ArrayList<String> addressList) {
		this.addressList = addressList;
	}
	
	
	

}

