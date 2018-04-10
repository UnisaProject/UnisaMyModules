package za.ac.unisa.lms.tools.cronjobs.forms.util;
import java.util.logging.*;

public class LoggerClass {
	
	public Logger getLogger(String path){
	            Logger logger = Logger.getLogger("MyLog");  
                FileHandler fh;  
                try {  
                       // This block configure the logger with handler and formatter  
                       fh = new FileHandler(path);  
                       logger.addHandler(fh);
                       SimpleFormatter formatter = new SimpleFormatter();  
                       fh.setFormatter(formatter);  
                 } catch (Exception e) {  
                     return null;  
                 } 
              return logger;
     }


}
