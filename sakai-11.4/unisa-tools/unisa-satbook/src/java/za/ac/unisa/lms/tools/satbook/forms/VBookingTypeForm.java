package za.ac.unisa.lms.tools.satbook.forms;

import org.apache.struts.validator.ValidatorForm;

public class VBookingTypeForm extends ValidatorForm {
	
	private String bookingTypeId;
	private String bookingTypeDesc;
	private boolean bookingTypeAct;
	
	/**
	 * Returns the BookingTypeId.
	 * @return String
	 */
	
	public String getBookingTypeId() {
		return bookingTypeId;
	}
	
	/**
	 * Set the BookingTypeId.
	 * @param BookingTypeId to set
	 */
	public void setBookingTypeId(String bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}
	
	/**
	 * Returns the BookingTypeDesc.
	 * @return String
	 */
	
	public String getBookingTypeDesc() {
		return bookingTypeDesc;
	}
	
	/**
	 * Set the BookingTypeDesc.
	 * @param BookingTypeDesc to set
	 */
	public void setBookingTypeDesc(String bookingTypeDesc) {
		this.bookingTypeDesc = bookingTypeDesc;
	}
	
	/**
	 * Returns the BookingTypeActive.
	 * @return boolean
	 */
	
	public boolean getBookingTypeAct() {
		return bookingTypeAct;
	}
	
	/**
	 * Set the BookingTypeAct.
	 * @param BookingTypeAct to set
	 */
	public void setBookingTypeAct(boolean bookingTypeAct) {
		this.bookingTypeAct = bookingTypeAct;
	}

}
