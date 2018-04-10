package Dissh10h.Common;
 
import com.ca.gen80.jprt.*;
import com.ca.gen80.vwrt.*;
import java.io.Serializable;
 
public class Dissh10sLinksSuToStmInput extends beanBase
          implements serverInputIF, java.io.Serializable
{
  //--------------------------------------------------
  // Input view sent in the server request
 
  public Dissh10h.DISSH10S_IA importView;
 
 
  public Dissh10sLinksSuToStmInput () {
    super();
  }
 
  public void clearProperties() {
     if (importView != null) {
         importView.reset();
     }
  }
 
}
