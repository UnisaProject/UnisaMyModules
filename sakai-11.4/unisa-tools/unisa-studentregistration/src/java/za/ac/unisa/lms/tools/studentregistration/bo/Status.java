package za.ac.unisa.lms.tools.studentregistration.bo;

import java.math.BigDecimal;

public class Status {
	private String payDate;
	private BigDecimal payFee = BigDecimal.valueOf(0.00);
	private boolean payFull;
	private String payComment;
	
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public BigDecimal getPayFee() {
		return payFee;
	}
	public void setPayFee(BigDecimal payFee) {
		this.payFee = payFee;
	}
	public boolean isPayFull() {
		return payFull;
	}
	public void setPayFull(boolean payFull) {
		this.payFull = payFull;
	}
	public String getPayComment() {
		return payComment;
	}
	public void setPayComment(String payComment) {
		this.payComment = payComment;
	}
		
}
