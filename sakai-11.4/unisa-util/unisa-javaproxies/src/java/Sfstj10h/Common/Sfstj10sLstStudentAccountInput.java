package Sfstj10h.Common;
 
import com.ca.gen80.jprt.*;
import com.ca.gen80.vwrt.*;
import java.io.Serializable;
 
public class Sfstj10sLstStudentAccountInput extends beanBase
          implements serverInputIF, java.io.Serializable
{
  //--------------------------------------------------
  // Input view sent in the server request
 
  public Sfstj10h.SFSTJ10S_IA importView;
 
 
  public Sfstj10sLstStudentAccountInput () {
    super();
  }
 
  public void clearProperties() {
     if (importView != null) {
         importView.reset();
     }
  }
 
}
