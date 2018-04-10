package za.ac.unisa.lms.tools.tpustudentplacement.utils;

public class OperListener implements java.awt.event.ActionListener {
	private Exception exception = null;
	
	public OperListener() {
		exception = null;
	}

	public Exception getException() {
		return exception;
	}

	public void actionPerformed(java.awt.event.ActionEvent aEvent) {
		exception = new Exception(aEvent.getActionCommand());
	}
}