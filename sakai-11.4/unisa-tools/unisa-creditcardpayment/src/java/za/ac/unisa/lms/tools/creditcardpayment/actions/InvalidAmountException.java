package za.ac.unisa.lms.tools.creditcardpayment.actions;

public class InvalidAmountException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public  InvalidAmountException(String err){
		          super(err);
		         this.setErr(err);
	}
	private String err;

	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
	}
