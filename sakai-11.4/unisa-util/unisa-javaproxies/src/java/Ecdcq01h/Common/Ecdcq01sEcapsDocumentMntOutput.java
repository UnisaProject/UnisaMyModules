package Ecdcq01h.Common;
 
import com.ca.gen80.jprt.*;
import com.ca.gen80.vwrt.*;
import java.io.Serializable;
 
public class Ecdcq01sEcapsDocumentMntOutput extends beanBase
    implements serverOutputIF, java.io.Serializable
{
  //--------------------------------------------------
  // Output view returned from the server request
 
  public Ecdcq01h.ECDCQ01S_OA exportView;
 
 
  //--------------------------------------------------
  // constructors
 
    public Ecdcq01sEcapsDocumentMntOutput () {
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
