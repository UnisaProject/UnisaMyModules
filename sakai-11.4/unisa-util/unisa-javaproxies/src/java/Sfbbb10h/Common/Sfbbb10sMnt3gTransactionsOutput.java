package Sfbbb10h.Common;
 
import com.ca.gen80.jprt.*;
import com.ca.gen80.vwrt.*;
import java.io.Serializable;
 
public class Sfbbb10sMnt3gTransactionsOutput extends beanBase
    implements serverOutputIF, java.io.Serializable
{
  //--------------------------------------------------
  // Output view returned from the server request
 
  public Sfbbb10h.SFBBB10S_OA exportView;
 
 
  //--------------------------------------------------
  // constructors
 
    public Sfbbb10sMnt3gTransactionsOutput () {
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
