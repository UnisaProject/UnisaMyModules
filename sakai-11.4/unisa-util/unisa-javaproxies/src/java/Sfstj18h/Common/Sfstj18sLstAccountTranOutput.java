package Sfstj18h.Common;
 
import com.ca.gen80.jprt.*;
import com.ca.gen80.vwrt.*;
import java.io.Serializable;
 
public class Sfstj18sLstAccountTranOutput extends beanBase
    implements serverOutputIF, java.io.Serializable
{
  //--------------------------------------------------
  // Output view returned from the server request
 
  public Sfstj18h.SFSTJ18S_OA exportView;
 
 
  //--------------------------------------------------
  // constructors
 
    public Sfstj18sLstAccountTranOutput () {
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
