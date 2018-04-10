package Gistl01h.Common;
 
import com.ca.gen80.jprt.*;
import com.ca.gen80.vwrt.*;
import java.io.Serializable;
 
public class Gistl01sProxyForGistf01mInput extends beanBase
          implements serverInputIF, java.io.Serializable
{
  //--------------------------------------------------
  // Input view sent in the server request
 
  public Gistl01h.GISTL01S_IA importView;
 
 
  public Gistl01sProxyForGistf01mInput () {
    super();
  }
 
  public void clearProperties() {
     if (importView != null) {
         importView.reset();
     }
  }
 
}
