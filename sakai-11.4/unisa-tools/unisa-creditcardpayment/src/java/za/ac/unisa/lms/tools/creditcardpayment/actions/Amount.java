package za.ac.unisa.lms.tools.creditcardpayment.actions;

public abstract class Amount {
	              public String amountEntered;
	             public double amountToBePaid;
	             public double balance;
	           public void setAmountToBePaid(double amountToBePaid) {
            		this.amountToBePaid = amountToBePaid;
            	}
            	public abstract double getAmountToBePaid();
				public String getAmountEntered() {
					return amountEntered;
				}
				public double getBalance() {
					return balance;
				}
				public void setBalance(double balance) {
					this.balance = balance;
				}
 }