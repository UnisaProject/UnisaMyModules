package Exerq40h.Common;
 
import com.ca.gen80.jprt.*;
import com.ca.gen80.vwrt.*;
import java.io.Serializable;
 
public class Exerq40sRmkApplManagementInput extends beanBase
          implements serverInputIF, java.io.Serializable
{
  //--------------------------------------------------
  // Input view sent in the server request
 
  public Exerq40h.IMP52223_IA importView;
 
 
  public Exerq40sRmkApplManagementInput () {
    super();
  }
 
  public void clearProperties() {
     if (importView != null) {
         importView.reset();
     }
  }
 
}
