package za.ac.unisa.exceptions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JavaProxyExceptionListener implements ActionListener {

	private Exception exception = null;

	public JavaProxyExceptionListener() {
		exception = null;
	}

	public Exception getException() {
		return exception;
	}

	public void actionPerformed(ActionEvent aEvent) {
		exception = new Exception(aEvent.getActionCommand());
	}
}
