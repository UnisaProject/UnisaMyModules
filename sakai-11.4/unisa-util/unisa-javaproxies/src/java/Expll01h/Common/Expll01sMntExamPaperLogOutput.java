package Expll01h.Common;
 
import com.ca.gen80.jprt.*;
import com.ca.gen80.vwrt.*;
import java.io.Serializable;
 
public class Expll01sMntExamPaperLogOutput extends beanBase
    implements serverOutputIF, java.io.Serializable
{
  //--------------------------------------------------
  // Output view returned from the server request
 
  public Expll01h.EXPLL01S_OA exportView;
 
 
  //--------------------------------------------------
  // constructors
 
    public Expll01sMntExamPaperLogOutput () {
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
