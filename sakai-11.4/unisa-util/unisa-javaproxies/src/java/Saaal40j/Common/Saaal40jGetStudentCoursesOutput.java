package Saaal40j.Common;
 
import com.ca.gen80.jprt.*;
import com.ca.gen80.vwrt.*;
import java.io.Serializable;
 
public class Saaal40jGetStudentCoursesOutput extends beanBase
    implements serverOutputIF, java.io.Serializable
{
  //--------------------------------------------------
  // Output view returned from the server request
 
  public Saaal40j.SAAAL40S_OA exportView;
 
 
  //--------------------------------------------------
  // constructors
 
    public Saaal40jGetStudentCoursesOutput () {
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
