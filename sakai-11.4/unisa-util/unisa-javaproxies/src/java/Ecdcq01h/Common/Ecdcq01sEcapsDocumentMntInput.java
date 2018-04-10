package Ecdcq01h.Common;
 
import com.ca.gen80.jprt.*;
import com.ca.gen80.vwrt.*;
import java.io.Serializable;
 
public class Ecdcq01sEcapsDocumentMntInput extends beanBase
          implements serverInputIF, java.io.Serializable
{
  //--------------------------------------------------
  // Input view sent in the server request
 
  public Ecdcq01h.ECDCQ01S_IA importView;
 
 
  public Ecdcq01sEcapsDocumentMntInput () {
    super();
  }
 
  public void clearProperties() {
     if (importView != null) {
         importView.reset();
     }
  }
 
}
