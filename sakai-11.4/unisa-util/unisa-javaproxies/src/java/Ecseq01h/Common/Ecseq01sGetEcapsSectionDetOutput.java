package Ecseq01h.Common;
 
import com.ca.gen80.jprt.*;
import com.ca.gen80.vwrt.*;
import java.io.Serializable;
 
public class Ecseq01sGetEcapsSectionDetOutput extends beanBase
    implements serverOutputIF, java.io.Serializable
{
  //--------------------------------------------------
  // Output view returned from the server request
 
  public Ecseq01h.ECSEQ01S_OA exportView;
 
 
  //--------------------------------------------------
  // constructors
 
    public Ecseq01sGetEcapsSectionDetOutput () {
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
