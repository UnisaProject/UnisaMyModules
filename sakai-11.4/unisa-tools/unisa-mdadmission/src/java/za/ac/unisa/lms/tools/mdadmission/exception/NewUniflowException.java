package za.ac.unisa.lms.tools.mdadmission.exception;

import java.io.IOException;

public class NewUniflowException extends IOException {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message = null;
 
    public NewUniflowException() {
        super();
    }
 
    public NewUniflowException(String message, Throwable t) {
        super( message, t);
        this.message = message;
    }
    
    public NewUniflowException(String message) {
        super(message);
        this.message = message;
    }
 
    public NewUniflowException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return message;
    }
}