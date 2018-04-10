package Staae01h.Common;
 
import com.ca.gen80.jprt.*;
import com.ca.gen80.vwrt.*;
import java.io.Serializable;
 
public class Staae01sAppEnquireAdmissionOutput extends beanBase
    implements serverOutputIF, java.io.Serializable
{
  //--------------------------------------------------
  // Output view returned from the server request
 
  public Staae01h.STAAE01S_OA exportView;
 
 
  //--------------------------------------------------
  // constructors
 
    public Staae01sAppEnquireAdmissionOutput () {
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
