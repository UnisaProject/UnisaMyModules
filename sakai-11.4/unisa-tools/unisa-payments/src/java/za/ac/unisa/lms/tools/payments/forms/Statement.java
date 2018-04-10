package za.ac.unisa.lms.tools.payments.forms;

public class Statement {
	private String date;
	private String description;
	private String reference;
	private String allocation;
	private double debitAmount;
	private double creditAmount;
	private double balanceAmount;
	private double amount;
	
	
	public String getAllocation() {
		return allocation;
	}
	public void setAllocation(String allocation) {
		this.allocation = allocation;
	}
	public double getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(double debitAmount) {
		this.debitAmount = debitAmount;
	}
	public double getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}
	public double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	/**
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return Returns the date.
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date The date to set.
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the reference.
	 */
	public String getReference() {
		return reference;
	}
	/**
	 * @param reference The reference to set.
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}
	

}
