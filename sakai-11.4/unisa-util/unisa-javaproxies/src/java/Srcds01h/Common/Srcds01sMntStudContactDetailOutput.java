package Srcds01h.Common;
 
import com.ca.gen80.jprt.*;
import com.ca.gen80.vwrt.*;
import java.io.Serializable;
 
public class Srcds01sMntStudContactDetailOutput extends beanBase
    implements serverOutputIF, java.io.Serializable
{
  //--------------------------------------------------
  // Output view returned from the server request
 
  public Srcds01h.SRCDS01S_OA exportView;
 
 
  //--------------------------------------------------
  // constructors
 
    public Srcds01sMntStudContactDetailOutput () {
       super();
    }
 
    public void printit() {
    }
 
    public void setDefaultValues() {
    }
 
    public void clearOutputProperties() {
        if (exportView != null) {
            exportView.reset();
        }
    }
 
}
