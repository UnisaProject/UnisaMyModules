package Sfrrf03h.Common;
 
import com.ca.gen80.jprt.*;
import com.ca.gen80.vwrt.*;
import java.io.Serializable;
 
public class Sfrrf03sMntOnlineCcPaymentsInput extends beanBase
          implements serverInputIF, java.io.Serializable
{
  //--------------------------------------------------
  // Input view sent in the server request
 
  public Sfrrf03h.SFRRF03S_IA importView;
 
 
  public Sfrrf03sMntOnlineCcPaymentsInput () {
    super();
  }
 
  public void clearProperties() {
     if (importView != null) {
         importView.reset();
     }
  }
 
}
